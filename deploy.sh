#!/bin/bash

set -e

APP_USER="alekseev"
APP_DIR="/home/$APP_USER/app"
DEPLOY_DIR="/var/www/app"
REPO_URL="https://github.com/mikhailmusic/linux-deploy"
JAR_NAME="LinuxDeploy-App.jar"
SERVICE_NAME="spring-app"
JAR_SOURCE="target/LinuxDeploy-0.0.1-SNAPSHOT.jar"

echo "[1] Обновление пакетов..."
sudo apt update

echo "[2] Установка openssh-server, openjdk-17, maven, git, nginx..."
sudo apt install -y openssh-server openjdk-17-jdk maven git nginx

echo "[3] Создание рабочей директории..."
mkdir -p "$APP_DIR"
cd "$APP_DIR"

echo "[4] Клонирование репозитория..."
git clone "$REPO_URL"
cd linux-deploy

echo "[5] Сборка проекта..."
mvn clean package

echo "[6] Подготовка директории деплоя..."
sudo mkdir -p "$DEPLOY_DIR"
sudo cp "$JAR_SOURCE" "$DEPLOY_DIR/$JAR_NAME"
sudo chown -R $APP_USER:$APP_USER "$DEPLOY_DIR"
sudo chmod -R 755 "$DEPLOY_DIR"

echo "[7] Создание systemd-сервиса..."
SERVICE_FILE="/etc/systemd/system/$SERVICE_NAME.service"
sudo bash -c "cat > $SERVICE_FILE" <<EOF
[Unit]
Description=Spring Boot Linux Deploy App
After=network.target

[Service]
User=$APP_USER
WorkingDirectory=$DEPLOY_DIR
ExecStart=/usr/bin/java -jar $DEPLOY_DIR/$JAR_NAME
SuccessExitStatus=143
Restart=on-failure
RestartSec=5
StandardOutput=journal
StandardError=journal
SyslogIdentifier=$SERVICE_NAME
Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
EOF

echo "[8] Перезапуск systemd..."
sudo systemctl daemon-reload

echo "[9] Включение и запуск сервиса..."
sudo systemctl enable $SERVICE_NAME
sudo systemctl start $SERVICE_NAME

echo "[10] Настройка Nginx..."
NGINX_CONF="/etc/nginx/sites-available/$SERVICE_NAME"
sudo bash -c "cat > $NGINX_CONF" <<EOF
server {
    listen 80;
    server_name _;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOF

sudo rm /etc/nginx/sites-enabled/default
sudo ln -sf $NGINX_CONF /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx

echo "[11] Проверка статуса сервисов..."
sudo systemctl status spring-app --no-pager
sudo systemctl status nginx --no-pager


echo "Установка завершена. Приложение доступно по адресу http://$(hostname -I | awk '{print $2}')"
