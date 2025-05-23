# LinuxDeploy

### Обновление списка пакетов
```bash
sudo apt update
```

### Установка необходимых пакетов: JDK 17, Maven, Git и Nginx
```bash
sudo apt install -y openjdk-17-jdk maven git nginx
```

### Проверка версии Java
```bash
java -version
```

### Проверка версии Maven
```bash
mvn --version
```

### Создание рабочей директории проекта
```bash
mkdir -p /home/alekseev/app
cd /home/alekseev/app
```

### Клонирование репозитория
```bash
git clone https://github.com/mikhailmusic/linux-deploy
```

### Переход в директорию проекта
```bash
cd linux-deploy
```

### Сборка проекта под Maven
```bash
mvn clean package
```

### Подготовка директории для продакшн-развертывания
Назначение пользователя `alekseev` владельцем директории `/var/www/app`, изменение прав на 755.
```bash
sudo mkdir -p /var/www/app
sudo chown -R alekseev:alekseev /var/www/app
sudo chmod -R 755 /var/www/app
```

### Копирование JAR-файла в директорию развертывания
```bash
sudo cp target/LinuxDeploy-0.0.1-SNAPSHOT.jar /var/www/app/LinuxDeploy-App.jar
```

### Создание конфигурации службы
```bash
sudo nano /etc/systemd/system/spring-app.service
```
Впишем:
```bash
[Unit]
Description=Spring Boot Linux Deploy App
After=network.target

[Service]
User=alekseev
WorkingDirectory=/var/www/app
ExecStart=/usr/bin/java -jar /var/www/app/LinuxDeploy-App.jar
SuccessExitStatus=143
Restart=on-failure
RestartSec=5
StandardOutput=journal
StandardError=journal
SyslogIdentifier=spring-app
  
Environment=SPRING_PROFILES_ACTIVE=prod

[Install]
WantedBy=multi-user.target
```

### Перечитывание конфигурации systemd для применения изменений
```bash
sudo systemctl daemon-reload
```

### Включение автозапуска службы
```bash
sudo systemctl enable spring-app
```

### Запуск службы Spring Boot приложения
```bash
sudo systemctl start spring-app
```

### Проверка статуса службы
```bash
sudo systemctl status spring-app
```

### Проверка прав доступа к JAR-файлу приложения
```bash
ls -l /var/www/app/LinuxDeploy-App.jar
```

### Проверка работы веб-приложения
```bash
curl http://localhost:8080/health
```


### Создание конфигурации Nginx
```bash
sudo nano /etc/nginx/sites-available/spring-app
```
Впишем:
```bash
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
```

### Удалить или отключить дефолтный сайт (освободить порт 80)
```bash
sudo rm /etc/nginx/sites-enabled/default
```
Или
```bash
sudo unlink /etc/nginx/sites-enabled/default
```

### Создание символической ссылки для активации конфигурации Nginx
```bash
sudo ln -s /etc/nginx/sites-available/spring-app /etc/nginx/sites-enabled
```

### Проверка конфигурации Nginx
```bash
sudo nginx -t
```

### Перезапуск Nginx для применения изменений
```bash
sudo systemctl restart nginx
```

### Проверка статуса службы Nginx
```bash
sudo systemctl status nginx
```

### Проверка работы веб-приложения на 80 порту
```bash
curl http://localhost:80/health
```

### Проверка работы с Windows:
1. Проброс порта 80 и по localhost
2. По ip виртульной машины

------------------------------------------------

## Доп. команды

### Сохранение истории команд
```bash
history > ~/deploy_history.txt
```

### Остановка, запуск и перезапуск сервиса
```bash
sudo systemctl stop spring-app
sudo systemctl restart spring-app
```

### Отключение автозапуска
```bash
sudo systemctl disable spring-app
```

### Просмотр логов службы `spring-app.service` из журнала systemd.
```bash
journalctl -u spring-app.service
```

### Фильтрация логов по ключевому слову `ERROR` для службы `spring-app.service`.
```bash
journalctl -u spring-app.service | grep ERROR
```

### Удаление службы
```bash
sudo systemctl stop spring-app
sudo systemctl disable spring-app
sudo rm /etc/systemd/system/spring-app.service
sudo systemctl daemon-reload
```

### Удаление Nginx-конфига при деинсталляции
```bash
sudo rm /etc/nginx/sites-enabled/spring-app
sudo rm /etc/nginx/sites-available/spring-app
sudo systemctl reload nginx
```

### Удаление директории развертывания
```bash
sudo rm -rf /var/www/app
```
### Проверка, что порт 8080 слушается
```bash
sudo lsof -i :8080
```

### Открытие порта
```bash
sudo ufw allow 80/tcp
```

### Если проблемы с сетью
```bash
ip a
networkctl list
networkctl status
sudo ip link set enp0s8 up
sudo nano /etc/netplan/50-cloud-init.yaml
```
```bash
enp0s8:
    dhcp4: no
    addresses: [192.168.56.104/24]
    nameservers:
        addresses: [8.8.8.8]
```
```bash
sudo netplan apply

sudo systemctl restart nginx
systemctl status nginx
```

### Установить JAVA_HOME на JDK 17
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
```

Ошибки:
1. При установке случайно не выбрать много CPU, VBox криво работает :)
2. Очистить навсякий `C:\Users\{Пользователь}\.ssh`


## Скриптом
```bash
wget -4 https://raw.githubusercontent.com/mikhailmusic/linux-deploy/refs/heads/main/deploy.sh
chmod +x deploy.sh
./deploy.sh
```