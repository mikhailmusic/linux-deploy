# LinuxDeploy

## Обновление списка пакетов
Обновляет список доступных пакетов в системе.
```bash
sudo apt update
```

## Установка необходимых пакетов
Устанавливает JDK 17, Maven, Git и Nginx.
```bash
sudo apt install -y openjdk-17-jdk maven git nginx
```

## Проверка версии Java
Проверяет установленную версию Java.
```bash
java -version
```

## Проверка версии Maven
Проверяет установленную версию Maven.
```bash
mvn --version
```

## Создание рабочей директории проекта
Создает директорию для приложения на локальной машине. Переходит в директорию с проектом.
```bash
mkdir -p /home/alekseev/app
cd /home/alekseev/app
```

## Клонирование репозитория
Клонирует репозиторий с исходным кодом приложения.
```bash
git clone https://github.com/mikhailmusic/linux-deploy
```

## Переход в директорию проекта
Переходит в директорию склонированного проекта.
```bash
cd linux-deploy
```

## Сборка проекта
Выполняет очистку и сборку проекта с помощью Maven.
```bash
mvn clean package
```

## Подготовка директории для продакшн-развертывания
Создает директорию для развертывания приложения. Назначает пользователя `alekseev` владельцем директории `/var/www/app`.
```bash
sudo mkdir -p /var/www/app
sudo chown -R alekseev:alekseev /var/www/app
sudo chmod -R 755 /var/www/app
```

## Копирование JAR-файла
Копирует собранный JAR-файл в директорию развертывания.
```bash
sudo cp target/LinuxDeploy-0.0.1-SNAPSHOT.jar /var/www/app/LinuxDeploy-App.jar
```

## Создание конфигурации службы
Открывает файл для создания конфигурации службы Spring Boot приложения.
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

## Перечитывание конфигурации systemd
Перечитывает конфигурацию systemd для применения изменений.
```bash
sudo systemctl daemon-reload
```

## Включение автозапуска службы
Включает автозапуск службы при загрузке системы.
```bash
sudo systemctl enable spring-app
```

## Запуск службы
Запускает службу Spring Boot приложения.
```bash
sudo systemctl start spring-app
```

## Проверка статуса службы
Проверяет текущий статус службы.
```bash
sudo systemctl status spring-app
```

## Проверка прав доступа к JAR-файлу
Проверяет права доступа к JAR-файлу приложения.
```bash
ls -l /var/www/app/LinuxDeploy-App.jar
```

## Создание конфигурации Nginx
Открывает файл для создания конфигурации Nginx.
```bash
sudo nano /etc/nginx/sites-available/spring-app
```
Впишем:
```bash
server {
  listen 80;
  server_name spring-app.com;

  location / {
     proxy_pass http://localhost:8080;
     proxy_set_header Host $host;
     proxy_set_header X-Real-IP $remote_addr;
     proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     proxy_set_header X-Forwarded-Proto $scheme;
  }
}
```

## Создание символической ссылки
Создает символическую ссылку для активации конфигурации Nginx.
```bash
sudo ln -s /etc/nginx/sites-available/spring-app /etc/nginx/sites-enabled
```

## Проверка конфигурации Nginx
Проверяет корректность конфигурации Nginx.
```bash
sudo nginx -t
```

## Перезапуск Nginx
Перезапускает Nginx для применения изменений.
```bash
sudo systemctl restart nginx
```

## Проверка статуса Nginx
Проверяет текущий статус службы Nginx.
```bash
sudo systemctl status nginx
```




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

### Просмотр логов службы
Выводит логи для указанной службы `spring-app.service` из журнала systemd.
```bash
journalctl -u spring-app.service
```

### Фильтрация логов по ключевому слову
Ищет записи в журнале systemd, содержащие строку `ERROR` для службы `spring-app.service`.
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

### Удаление директории развертывания:
```bash
sudo rm -rf /var/www/app
```
### Проверка, что порт 8080 слушается:
```bash
sudo lsof -i :8080
```