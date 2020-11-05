//
//  LoginView.swift
//  IOS
//
//  Created by 김세현 on 2020/11/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct LoginView: View {
    
    @State var activeMain = false
    @State var activeSignUp = false
    
    @State var id = ""
    @State var pw = ""
    
    @State var alertVisible = false
    @State var title = ""
    @State var content = ""
    var body: some View {
        NavigationView{
            VStack{
                NavigationLink(destination: MainView(), isActive: $activeMain, label: {})
                NavigationLink(destination: SignUp1View(), isActive: $activeSignUp, label: {})
                VStack(spacing: 15){
                    VStack(spacing: 5){
                        TextField("아이디", text: $id)
                            .font(.system(size: 20))
                        Line(width: 2)
                    }
                    VStack(spacing: 5){
                        TextField("비밀번호", text: $pw)
                            .font(.system(size: 20))
                        Line(width: 2)
                    }
                    Button(action: {
                        AlamofireService.shared.login(userId: id, userPw: pw){ i in
                            switch i {
                            case 0:
                                title = "서버 오류"
                                content = "잠시후에 로그인해주세요."
                                alertVisible = true
                                break
                            case 1:
                                UserDefaults.standard.set(id,forKey: "id")
                                UserDefaults.standard.set(pw,forKey: "pw")
                                activeMain = true
                                break
                            case 2:
                                title = "ID/PW 오류"
                                content = "아이디 및 비밀번호를 확인해주세요."
                                alertVisible = true
                                break
                            default:
                                break
                            }
                        }
                    }, label: {
                        Text("로그인")
                            .font(.system(size: 20))
                            .foregroundColor(Color.white)
                    })
                    .frame(width: UIScreen.main.bounds.width * 0.7, height: 40)
                    .background(Colors.primary)
                    .cornerRadius(45)
                    .alert(isPresented: $alertVisible){
                        Alert(title: Text(title), message: Text(content))
                    }
                    
                    Button(action: {
                        activeSignUp = true
                    }, label: {
                        Text("회원가입")
                            .font(.system(size: 15))
                            .foregroundColor(Colors.grey600)
                    })
                    .frame(width: UIScreen.main.bounds.width * 0.7, height: 40)
                }
                .frame(width: UIScreen.main.bounds.width * 0.7)
                .padding()
            }
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(.all)
    }
}

struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView()
    }
}
