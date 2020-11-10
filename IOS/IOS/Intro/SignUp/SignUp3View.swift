//
//  SignUp3View.swift
//  IOS
//
//  Created by 김세현 on 2020/11/06.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUp3View: View {
    
    @State var university : String
    @State var department : String
    @State var webmail : String
    
    @State var id = ""
    @State var pw = ""
    @State var pwConfirm = ""
    @State var nickname = ""
    @State var birthday = "선택"
    @State var city = "선택"
    @State var gender = ""
    @State var enterYear = ""
    
    @State var datePickerVisible = false
    @State var date = Date()
    
    @State var alertVisible = false
    
    var dateFormatter : DateFormatter {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        
        return formatter
    }
    
    
    
    var body: some View {
        ZStack{
            VStack(spacing: 10){
                HStack{
                    Text("STEP 3 OF 4")
                    Spacer()
                }
                .padding()
                
                VStack{
                    HStack{
                        Text("아이디")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    
                    TextField("영문, 숫자 5~20자", text: $id)
                        .frame(height:30)
                        .padding(.bottom, 2)
                    Line(width: 2)
                }
                
                VStack{
                    HStack{
                        Text("비밀번호")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    
                    TextField("비밀번호", text: $pw)
                        .frame(height:30)
                        .padding(.bottom, 2)
                    Line(width: 2)
                }
                
                VStack{
                    HStack{
                        Text("비밀번호 확인")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    
                    TextField("비밀번호 확인", text: $pwConfirm)
                        .frame(height:30)
                        .padding(.bottom, 2)
                    Line(width: 2)
                }
                
                VStack{
                    HStack{
                        Text("닉네임")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    
                    TextField("영문, 숫자 3~10자", text: $nickname)
                        .frame(height:30)
                        .padding(.bottom, 2)
                    Line(width: 2)
                }
                
                
                VStack{
                    HStack{
                        Text("생년월일")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    ZStack{
                        Button(action: {
                            datePickerVisible = true
                        }, label: {
                            HStack{
                                Text(birthday)
                                    .font(.system(size:20))
                                    .foregroundColor(Colors.grey500)
                                Spacer()
                                Image("more_icon")
                                    .resizable()
                                    .frame(width: 12,height: 12)
                            }
                            .padding(.horizontal)
                        })
                        .padding(.bottom, 2)
                    }
                    Line(width: 2)
                }
                
                VStack{
                    HStack{
                        Text("거주지")
                            .font(.system(size: 20))
                        Spacer()
                    }
                    ZStack{
                        Button(action: {
                            alertVisible = true
                        }, label: {
                            HStack{
                                Text(city)
                                    .font(.system(size:20))
                                    .foregroundColor(Colors.grey500)
                                Spacer()
                                Image("more_icon")
                                    .resizable()
                                    .frame(width: 12,height: 12)
                            }
                            .padding(.horizontal)
                        })
                        .padding(.bottom, 2)
                        .sheet(isPresented: $alertVisible){
                            EditDetailProfileView(title: "거주지", data: $city)
                        }
                    }
                    Line(width: 2)
                }
                
                VStack{
                    GeometryReader{ g in
                        HStack{
                            Button(action: {
                                gender = "남성"
                            }, label: {
                                Text("남성")
                                    .padding(.vertical, 5)
                                    .font(.system(size: 20))
                                    .frame(width: g.size.width * 0.5)
                                    .foregroundColor(gender == "남성" ? Color.white : Color.black)
                                    .background(RoundedCorners(tl: 15, tr: 0, bl: 15, br: 0).fill(gender == "남성" ? Colors.primary : Color.white))
                            })
                            Button(action: {
                                gender = "여성"
                            }, label: {
                                Text("여성")
                                    .padding(.vertical, 5)
                                    .font(.system(size: 20))
                                    .frame(width: g.size.width * 0.5)
                                    .foregroundColor(gender == "여성" ? Color.white : Color.black)
                                    .background(RoundedCorners(tl: 0, tr: 15, bl: 0, br: 15).fill(gender == "여성" ? Colors.primary : Color.white))
                            })
                        }
                        .overlay(
                            RoundedRectangle(cornerRadius: 15)
                                .stroke(Colors.primary, lineWidth: 2)
                        )
                    }
                }
                
                VStack{
                    Text("학교 정보")
                    Line(width: 2)
                }
                .padding()
            }
            
            if datePickerVisible {
                VStack{
                    Spacer()
                    VStack{
                        DatePicker("", selection: $date, displayedComponents: .date)
                            .datePickerStyle(WheelDatePickerStyle())
                        HStack(spacing: 15){
                            Spacer()
                            Button(action: {
                                datePickerVisible = false
                            }, label: {
                                Text("취소")
                                    .font(.system(size: 20))
                                    .foregroundColor(Color.red)
                                    .padding()
                            })
                            Button(action: {
                                var str = dateFormatter.string(from: date)
                                birthday = String(str.split(separator: " ")[0])
                                
                                datePickerVisible = false
                            }, label: {
                                Text("확인")
                                    .font(.system(size: 20))
                                    .foregroundColor(Colors.primary)
                                    .padding()
                            })
                        }
                            
                    }
                    .background(Color.white)
                    Spacer()
                }
                .padding()
                .background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
        .padding()
        .ignoresSafeArea()
        .navigationTitle("")
        .navigationBarHidden(true)
        .padding(.top, 30)
        .onAppear(){
            print(self.university,self.department,self.webmail)
        }
    }
}
