<div align="center">

[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Open+sans&weight=500&size=60&pause=2000&color=3B82F6&center=true&vCenter=true&width=420&height=65&lines=%D0%9C%D0%BE%D1%81.%D0%9A%D1%83%D0%BB%D1%8C%D1%82%D1%83%D1%80%D0%B0)](https://git.io/typing-svg)
</div>

<p align="center" style="margin-top: 10px">
  <i align="center">Узнавайте новую информацию в сфере культуры в Москве🎨</i><br>
  <i align="center"><sub>При поддержке проекта "Лидеры Цифровой Трансформации 2023"<sub></i>
</p>
<br>
    
## Ссылки на ресурсы
* Ссылка на бекенд приложение (с документацией OpenApi) http://94.139.255.120/api/swagger-ui/index.html
* Ссылка на Google Drive с apk файлом: https://drive.google.com/drive/folders/1nUzq-y_xRmy-eyhU_t0Pw7G7tndwRmo7?usp=sharing
* Ссылка на админ-панель: http://178.170.196.129
* Ссылка на репозиторий с кодом для Frontend (React Native): https://github.com/MatveyBatishchev/Frontend-Hackaton2023
* Ссылка на репозиторий с кодом для админ-панели: https://github.com/MatveyBatishchev/AdminPanel-Hackaton2023
> Проекты были развёрнуты в облачном хранилище при помощи сервиса **SberCloud**

## Введение

**Мос.Культура** - это образовательно-развлекательное мобильное приложение, которое объединит информацию о школах искусств и
позволит москвичам:

* узнавать интересные факты об искусстве в различных сферах;
* получать актуальную информацию о проектах, грантах и премиях в сфере культуры и искусства;
* выполнять тестовые задания и получать рекомендации о релевантном образовательном курсе;
* делиться опытом

Испытайте **новый творческий опыт** с Мос.Культура

<details open>
<summary><strong>Наши возможности👇</strong></summary>
<br/>
    
* **📔Лента статьей** -  увлекательный и образовательный источник информации, предлагающий широкий спектр разделов с интересными рубриками, статьями и медиа о культурной сцене Москвы..
* **🧩Развлекательные тесты** - раздел предлагает пользователям захватывающую и интерактивную форму развлечения, сочетая образовательные элементы с увлекательными квизами и призами.
* **🗺️Карта Московских школ искусств** - интерактивная карта Москвы с
  образовательными учреждениями
* **🎓Онлайн курсы** - платформу, предлагающую широкий выбор интерактивных курсов для творческого саморазвития.
<br/>
<img src="/.github/assets/test_screen.png" style="width: 180px"/>&nbsp;&nbsp;&nbsp;
<img src="/.github/assets/schools_screen.png" style="width: 180px"/>&nbsp;&nbsp;&nbsp;
<img src="/.github/assets/courses_screen.png" style="width: 180px"/>&nbsp;&nbsp;&nbsp;
<img src="/.github/assets/article_screen.png" style="width: 180px"/>&nbsp;&nbsp;&nbsp;
</details>

## Развёртывание приложения при помощи Docker
Здесь содержится инструкции по запуску проекта Spring Boot с помощью Docker.<br>
Выполнив эти шаги, вы сможете контейнеризировать приложение и легко развернуть его в различных средах.

* Убедитесь, что у вас запущен Docker Engine
* Соберите образ Docker:
```
    docker build --tag mos_culture_backend .
```
> **Примечание**: Не забудьте включить точку (.) в конце команды, указывающую на текущий каталог.

* Разверните локально необходимые контейнеры:
```
    docker compose up -d
```
* Убедитесь, что приложение развернулось перейдя по локальному адресу, где находится OpenApi документация:
```
    http://localhost:8080/api/swagger-ui/index.html
```

## Вклад

Команда Мос.Культура состоит из единомышленников, которые тесно сотрудничают между собой.<br/>
Мы придерживаемся прозрачного процесса разработки и высоко ценим все мнения и предложения.📢<br/>
Независимо от того, предлагаете ли вы новые возможности, комментируете или
распространяете информацию - мы будем рады вашему вкладу Мос.Культура.

## Авторы✨

<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/MatveyBatishchev"><img src="https://avatars.githubusercontent.com/u/71509628?v=4" width="100px;" alt="Батищев Матвей"/><br /><sub><b>Батищев Матвей</b></sub></a><br/><span style="font-size: 12px">backend👨‍💻</span></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/TheDuke2021"><img src="https://avatars.githubusercontent.com/u/67224120?v=4" width="100px;" alt="Бараев Дамир"/><br /><sub><b>Бараев Дамир</b></sub></a><br/><span style="font-size: 12px">backend👨‍💻</span></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/r4nd0lph-c"><img src="https://avatars.githubusercontent.com/u/60665381?v=4" width="100px;" alt="Пафнутьев Роман"/><br /><sub><b>Пафнутьев Роман</b></sub></a><br/><span style="font-size: 12px">design🧑‍🎨</span></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/Volvram"><img src="https://avatars.githubusercontent.com/u/71634985?v=4" width="100px;" alt="Бобрович Юлия"/><br /><sub><b>Бобрович Юлия</b></sub></a><br/><span style="font-size: 12px">frontend👩‍💻</span></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/b8enly"><img src="https://avatars.githubusercontent.com/u/71564737?v=4" width="100px;" alt="Яблонская Софья"/><br /><sub><b>Яблонская Софья</b></sub></a><br/><span style="font-size: 12px">frontend👩‍💻</span></td>
    </tr>
  </tbody>
</table>




