package com.pagatodo.yaganaste.modules.register

class RegisterContracts {

    interface Presenter {
        fun initViews()
        fun showLoader(message: String)
        fun onUserCreated()
        fun onErrorService(message: String)
    }

    interface Iteractor {
        fun registerUserClient(name: String, surname: String, secondSurname: String, email: String, dateBirth: String,
                               placeBirth: String, genre: String, claveBirthPlace: String)
    }

    interface Router {
        fun presentMainScreen()
        fun presentOnboardingScreen()
    }
}