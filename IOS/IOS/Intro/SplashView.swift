//
//  SplashView.swift
//  IOS
//
//  Created by 김세현 on 2020/11/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SplashView: View {
    
    @State var activeLogin = false
    @State var activeMain = false
    
    var body: some View {
        NavigationView{
            VStack{
                NavigationLink(
                    destination: LoginView(),
                    isActive: $activeLogin,
                    label: {
                    })
                NavigationLink(
                    destination: MainView(),
                    isActive: $activeMain,
                    label: {
                    })
                Text("SPLASH")
            }
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            if UserDefaults.standard.string(forKey: "id") == nil {
                self.activeLogin = true
            }
            else {
                var userId = UserDefaults.standard.string(forKey: "id")
                var userPw = UserDefaults.standard.string(forKey: "pw")
                AlamofireService.shared.login(userId: userId!, userPw: userPw!){ i in
                    self.activeMain = true
                }
            }
        }
    }
    
}

struct SplashView_Previews: PreviewProvider {
    static var previews: some View {
        SplashView()
    }
}
