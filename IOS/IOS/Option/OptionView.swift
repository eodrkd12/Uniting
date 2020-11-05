//
//  OptionView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct OptionView: View {
    
    @State var blockMyDept = false
    @State var editProfileVisible = false
    
    var body: some View {
        VStack(spacing: 35){
            NavigationLink(
                destination: EditProfileView(),
                isActive: $editProfileVisible,
                label: {})
            Button(action: {
                editProfileVisible = true
            }, label: {
                Text("프로필 편집")
                    .font(.system(size: 22))
                    .foregroundColor(Colors.grey600)
                    .frame(width: UIScreen.main.bounds.width * 0.8, height: 50)
                    .overlay(
                        RoundedRectangle(cornerRadius: 10)
                            .stroke(Colors.grey400, lineWidth: 2)
                    )
            })
            .padding(.top,20)
            
            VStack(spacing: 5){
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("알림설정")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("같은 학과 만나지 않기")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                    Toggle(isOn: $blockMyDept, label: {
                    })
                    .frame(width: 20)
                    .padding(.trailing, 20)
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        UserDefaults.standard.removeObject(forKey: "id")
                        UserDefaults.standard.removeObject(forKey: "pw")
                    }, label: {
                        Text("로그아웃")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
            }
            .frame(width: UIScreen.main.bounds.width * 0.9)
            
            Line(width: 2)
            
            VStack(spacing: 5){
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("공지사항")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("문의사항")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("앱 버전")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("이용약관")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
                
                Line(width: 1)
                
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("개인정보 처리방침")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey600)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
            }
            .frame(width: UIScreen.main.bounds.width * 0.9)
            
            Line(width: 2)
            
            VStack(spacing: 10){
                HStack{
                    Button(action: {
                        
                    }, label: {
                        Text("회원탈퇴")
                            .font(.system(size: 22))
                            .foregroundColor(Color.red)
                    })
                    Spacer()
                }
                .padding(.horizontal,5)
            }
            .frame(width: UIScreen.main.bounds.width * 0.9)
            Spacer()
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
    }
}

struct OptionView_Previews: PreviewProvider {
    static var previews: some View {
        OptionView()
    }
}
