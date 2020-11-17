//
//  SignUp4View.swift
//  IOS
//
//  Created by 김세현 on 2020/11/10.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUp4View: View {
    
    @Environment(\.presentationMode) var presentationMode : Binding<PresentationMode>
    
    @State var id : String
    @State var pw : String
    @State var height = "선택"
    @State var hobby = "선택"
    @State var personality = "선택"
    @State var introduce = ""
    
    @State var alertMessage = ""
    @State var alertVisible = false
    
    @State var activeNext = false
    
    var body: some View {
        VStack(spacing: 10){
            NavigationLink(
                destination: MainView(),
                isActive: $activeNext,
                label: {
                })
            
            HStack{
                Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    Text("뒤로")
                        .foregroundColor(Colors.primary)
                })
                Spacer()
            }
            HStack{
                Text("STEP 4 OF 4 (선택)")
                Spacer()
            }
            
            Text("프로필을 완성시키면 매칭률이 올라가요!\n세부정보를 작성해보세요.")
                .foregroundColor(Colors.grey500)
            
            
            EditableRow(title: "키", content: $height)
            EditableRow(title: "취미", content: $hobby)
            EditableRow(title: "성격", content: $personality)
            HStack(alignment: .top){
                Text("소개")
                    .font(.system(size:18))
                    .foregroundColor(Colors.grey500)
                    .frame(width: 80)
                Spacer()
                
                MultilineTextField(text: "자신을 소개해주세요.(10자 이상)", fontSize: 18, content: $introduce)
                    .frame(height:UIScreen.main.bounds.height * 0.2)
                    .overlay(RoundedRectangle(cornerRadius: 15).stroke(Colors.grey300, lineWidth: 2))
            }
            .padding(5)
            Spacer()
            Group{
                Button(action: {
                    if height == "선택" || hobby == "선택" || personality == "선택" || introduce.count < 10 {
                        alertMessage = "항목을 선택해주세요."
                        alertVisible = true
                    }
                    else {
                        AlamofireService.shared.updateProfileInfo(id: id, height: height, hobby: hobby, personality: personality, introduce: introduce){ result in
                            AlamofireService.shared.login(userId: id, userPw: pw){ login in
                                UserDefaults.standard.set(id,forKey: "id")
                                UserDefaults.standard.set(pw,forKey: "pw")
                                activeNext = true
                            }
                        }
                    }
                }, label: {
                    Text("가입완료")
                        .padding()
                        .frame(width: UIScreen.main.bounds.width * 0.8)
                        .foregroundColor(Color.white)
                        .font(.system(size: 20))
                        .background(Colors.primary)
                        .cornerRadius(45)
                })
                .alert(isPresented: $alertVisible, content: {
                    Alert(title: Text(alertMessage))
                })
                
                Button(action: {
                    AlamofireService.shared.login(userId: id, userPw: pw){ login in
                        UserDefaults.standard.set(id,forKey: "id")
                        UserDefaults.standard.set(pw,forKey: "pw")
                        activeNext = true
                    }
                }, label: {
                    Text("다음에")
                        .foregroundColor(Colors.grey500)
                        .font(.system(size: 20))
                })
            }
        }
        .padding()
        .ignoresSafeArea()
        .navigationTitle("")
        .navigationBarHidden(true)
        .padding(.top, 10)
    }
}

struct SignUp4View_Previews: PreviewProvider {
    static var previews: some View {
        SignUp4View(id : "", pw: "")
    }
}
