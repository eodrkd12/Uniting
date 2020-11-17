//
//  SplashView.swift
//  IOS
//
//  Created by 김세현 on 2020/11/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SplashView: View {
    
    @Environment(\.presentationMode) var presentationMode : Binding<PresentationMode>
    
    @State var activeLogin = false
    @State var activeMain = false
    @State var alertVisible = false
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
                    .alert(isPresented: $alertVisible, content: {
                        Alert(title: Text("실행할 수 없습니다."), message: Text("업데이트 체크를 해주세요.")
                              ,dismissButton: .default(Text("종료하기"), action: {
                                exit(0)
                              }))
                    })
            }
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            let version = (Bundle.main.infoDictionary!["CFBundleShortVersionString"] as? String).unsafelyUnwrapped
            
            AlamofireService.shared.getVersion(){ versionModel in
                if version == versionModel.app_version || versionModel.update_issue == "selective" {
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
                else {
                    alertVisible = true
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
