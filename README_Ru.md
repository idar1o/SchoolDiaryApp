
# Электронный дневник  

Приложение электронного дневника, выполненное в стиле WhatsApp. Этот проект включает в себя как мобильное приложение, так и сервер (в данный момент отключен). Приложение разработано для учителей, предоставляя ключевые функции управления классами.  

## Функции  

- **Только аккаунт учителя** – В настоящее время поддерживаются только учетные записи учителей.  
- **Система входа** – Безопасная аутентификация для учителей.  
- **Управление классами** – Просмотр доступных классов, назначенных учителю.  
- **Список учеников** – Доступ к списку учеников в каждом классе.  
- **Объявления** – Создание и публикация объявлений для учеников.  
- **Задания** – Возможность выдавать задания ученикам.  
- **Управление оценками** – Просмотр оценок учеников и возможность ставить оценки (от 2 до 5).  
- **Чат в реальном времени** – Мессенджер в стиле WhatsApp с использованием WebSockets для общения между учителем и учениками.  

## Статус сервера  
Бэкенд-сервер в данный момент отключен. В будущих обновлениях возможен запуск сервера и добавление новых функций.  

## Используемые технологии  
- **Kotlin** – Основной язык программирования  
- **Clean Architecture** – Четко структурированная архитектура приложения  
- **MVVM** – Паттерн Model-View-ViewModel для управления состоянием  
- **Coroutines/Flow** – Асинхронное программирование  
- **Jetpack Compose** – Современный UI-инструментарий  
- **WebSockets** – Функциональность чата в реальном времени  

## Скриншот
https://github.com/user-attachments/assets/2f4fb95e-d302-4745-8f89-6d69531a1e51

## Установка  
1. Клонируйте репозиторий:  
   ```bash
   git clone https://github.com/idar1o/SchoolDiaryApp.git
   ```
2. Откройте проект в Android Studio.  
3. Запустите приложение на эмуляторе или реальном устройстве.  

## Вклад в проект  
Вы можете внести свой вклад, открыв issue или отправив pull request!  

## Лицензия  
This project is licensed under the MIT License - see the LICENSE file for details.
MIT License

Copyright (c) 2024 Aidar

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

Created with ❤️ and Kotlin.
