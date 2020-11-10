//
//  SignUp2View.swift
//  IOS
//
//  Created by 김세현 on 2020/11/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUp2View: View {
    @State var university : String
    @State var universityMail : String
    @State var department : String

    @State var webmail = ""
    @State var code = ""
    @State var editCode = ""
    @State var verified = false
    
    @State var btnText = "전송"
    
    @State var activeNext = false
    
    
    var body: some View {
        VStack(spacing: 10){
            NavigationLink(destination: SignUp3View(university: university, department: department, webmail: webmail+universityMail),isActive: $activeNext, label: {})
            HStack{
                Text("STEP 2 OF 4")
                Spacer()
            }
            .padding()
            
            VStack{
                HStack{
                    Text("학교 이메일을 확인해 주세요.")
                        .font(.system(size: 25))
                    Spacer()
                }
                GeometryReader{ g in
                    HStack{
                        TextEditor(text: $webmail)
                            .frame(width: g.size.width * 0.35, height: 20)
                            .font(.system(size: 18))
                            .foregroundColor(Colors.grey700)
                            .multilineTextAlignment(.center)
                        Text(universityMail)
                            .frame(width: g.size.width * 0.45, height: 30)
                            .font(.system(size: 18))
                            .foregroundColor(Colors.grey500)
                        Button(action: {
                            btnText = "재전송"
                            code = ""
                            for i in 0..<6 {
                                code += "\(Int.random(in: 0..<10))"
                            }
                            print(code)
                            AlamofireService.shared.sendMail(to: "\(webmail)\(universityMail)", code: code){ result in
                                
                            }
                            
                        }, label: {
                            Text(btnText)
                                .frame(width: g.size.width * 0.2, height: 30)
                                .font(.system(size: 18,weight: .bold))
                                .foregroundColor(Color.white)
                                .background(Colors.primary)
                                .cornerRadius(15)
                        })
                    }
                }
                .frame(height: 30)
                .padding(.vertical, 2)
                Line(width: 2)
            }
            .padding()
            
            VStack{
                HStack{
                    Text("인증번호")
                        .font(.system(size: 25))
                    Spacer()
                }
                GeometryReader{ g in
                    HStack{
                        TextEditor(text: $editCode)
                            .frame(width: g.size.width * 0.8, height: 30)
                            .font(.system(size: 18))
                            .foregroundColor(Colors.grey700)
                            .multilineTextAlignment(.center)
                        Button(action: {
                            if editCode == code {
                                verified = true
                            }
                        }, label: {
                            Text("인증")
                                .frame(width: g.size.width * 0.2, height: 30)
                                .font(.system(size: 18,weight: .bold))
                                .foregroundColor(Color.white)
                                .background(editCode.count == 6 ? Colors.primary : Colors.grey500)
                                .cornerRadius(15)
                        })
                    }
                }
                .frame(height: 30)
                .padding(.vertical, 2)
                Line(width: 2)
            }
            .padding()
            
            Button(action: {
                if verified == true {
                    activeNext = true
                }
            }, label: {
                Text("다음")
                    .font(.system(size: 25))
                    .foregroundColor(Color.white)
                    .padding(5)
                    .frame(width: UIScreen.main.bounds.width * 0.8)
                    .background(verified == false ? Colors.grey500 : Colors.primary)
                    .cornerRadius(45)
            })
            
            
            Spacer()
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(.all)
        .padding(.top, 30)
        .padding(.horizontal, 20)
        .onAppear(){
        }
    }
}
