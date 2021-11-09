# CronoAgua - Gerenciador de hidratação

* Uma aplicação desenvolvida para o estudo da linguagem kotlin e diversas técnicas da programação mobile para Android.

<img src="/records/notify.gif" width="700">

### Tecnologias

* Android
* Kotlin
* Padrão MVVM
* Clean Architecture
* App Icon
* Fragments
* Navigation
* Drawer Layout
* Toolbar
* ViewModel
* SQLite
* Room with TypeConverters
* CountdownTimer
* Kotlin Coroutines
* Notify
   * Vibrate
   * WorkerRequest
   * BrodcastReceiver


### Funcionamento

* Ao abrir, o usuário deve inserir seus dados na tela Usuário.
* Existe uma configuração padrão que o usuário pode alterar se deseja ou não ser notificado, e os horários de rotina de sono.
* Com base nas informações do usuário e de suas configurações é realizado um cálculo que define quantos litros a pessoa deve inserir no dia.
* A cada 1h o app notifica o usuário para lembrar de sua hidratação, após isso, o usuário deve clicar em beber para atualizar o contador.
* Caso o usuário altere alguma informação no seu perfil Usuário ou Configurações, o cálculo e notificações são atualizadas.

<img src="/records/drink.gif" width="700">

### Em Testes

* Notificar o usuário de acordo com o horário em que acorda setado na tela configurações.

<img src="/records/configs.gif" width="700">
