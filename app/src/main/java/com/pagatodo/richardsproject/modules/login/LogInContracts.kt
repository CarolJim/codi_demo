package com.pagatodo.richardsproject.modules.login

class LogInContracts {

    interface Presenter {
        fun onLogIn()
        fun onLogOut()
        fun onErrorService(message: String)
        fun showLoader(message: String)
    }

    interface Iteractor {
        fun closeSession()
        fun startSession(user: String, pss: String)
    }

    interface Router {
        fun presentOnboarding()
        fun presentMainScreen()
    }
}