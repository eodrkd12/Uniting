//
//  EditProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/10/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditProfileView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var nickname = ""
    @State var birthday = ""
    @State var city = ""
    @State var height = ""
    @State var hobby = ""
    @State var personality = ""
    @State var introduce = ""
    @State var signdate = ""
    
    @State var university = ""
    @State var department = ""
    @State var webmail = ""
    
    @State var datePickerVisible = false
    @State var date = Date()
    
    var dateFormatter : DateFormatter {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        
        return formatter
    }
    
    var body: some View {
        ZStack{
            VStack{
                HStack{
                    HStack{
                        Image("black_back_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                            .padding()
                            .onTapGesture {
                                self.presentationMode.wrappedValue.dismiss()
                            }
                        Spacer()
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40, alignment: .leading)
                    HStack{
                        Text("내정보")
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40)
                    HStack{
                        Spacer()
                        Button(action:{
                            AlamofireService.shared.updateUserInfo(userId: UserInfo.shared.ID,nickname: nickname,birthday: birthday,city: city,height: height,hobby: hobby,personality: personality,introduce: introduce){ result in
                                if result.result == "success" {
                                    presentationMode.wrappedValue.dismiss()
                                }
                            }
                        },
                        label: {
                            Text("저장")
                                .font(.system(size: 20))
                                .foregroundColor(Colors.primary)
                                .padding()
                        })
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40)
                }
                
                Line(width: 2)
                    .padding(.top,10)
                
                
                VStack(spacing: 5){
                    HStack{
                        Text("프로필 정보")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                    }
                    .padding(.leading, 10)
                    .padding(.vertical, 10)
                    
                    VStack(){
                        EditableRow(title: "닉네임", content: $nickname)
                        EditableRow(title: "생년월일", content: $birthday)
                            .onTapGesture {
                                datePickerVisible = true
                            }
                        EditableRow(title: "거주지", content: $city)
                        EditableRow(title: "키", content: $height)
                        EditableRow(title: "취미", content: $hobby)
                        EditableRow(title: "성격", content: $personality)
                        EditableRow(title: "소개", content: $introduce, isIntroduce: true)
                        NotEditableRow(title: "가입일자", content: $signdate)
                    }
                }
                .padding()
                
                Line(width: 2)
                
                VStack(spacing: 5){
                    HStack{
                        Text("학교 정보")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                    }
                    .padding(.leading, 10)
                    .padding(.vertical, 10)
                    
                    VStack{
                        NotEditableRow(title: "학교", content: $university)
                        NotEditableRow(title: "학과", content: $department)
                        NotEditableRow(title: "웹메일", content: $webmail)
                    }
                }
                .padding()
                
                Spacer()
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
                                let str = dateFormatter.string(from: date)
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
        .navigationTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            AlamofireService.shared.getProfile(userId: UserInfo.shared.ID){ profile in
                self.nickname = profile.user_nickname
                self.birthday = profile.user_birthday
                self.city = profile.user_city
                self.height = profile.user_height == nil ? "정보 없음" : profile.user_height!
                self.hobby = profile.user_hobby == nil ? "정보 없음" : profile.user_hobby!
                self.personality = profile.user_personality == nil ? "정보 없음" : profile.user_personality!
                self.introduce = profile.user_introduce == nil ? "정보 없음" : profile.user_introduce!
                self.signdate = String(profile.user_signdate.split(separator: " ")[0])
                
                self.university = profile.univ_name
                self.department = profile.dept_name
                self.webmail = profile.user_email
            }
        }
    }
}

struct EditProfileView_Previews: PreviewProvider {
    static var previews: some View {
        EditProfileView()
    }
}

struct EditableRow : View {
    
    @State var title : String
    @Binding var content : String
    
    @State var alertVisible = false
    @State var isIntroduce = false
    
    var body: some View {
        HStack{
            Text(title)
                .font(.system(size:18))
                .foregroundColor(Colors.grey500)
                .frame(width: 80)
            Spacer()
            ZStack{
                VStack{
                    if title == "닉네임" {
                        TextEditor(text: $content)
                            .font(.system(size: 18))
                            .foregroundColor(Colors.grey700)
                            .multilineTextAlignment(.center)
                            .frame(width: 200, height: 30)
                    }
                    else {
                        Text(content)
                            .font(.system(size:18))
                            .foregroundColor(Colors.grey700)
                            .truncationMode(.tail)
                            .lineLimit(1)
                            .frame(width: 200)
                    }
                    Line(width: 2)
                }
                HStack{
                    Spacer()
                    Image("more_icon")
                        .resizable()
                        .frame(width: 12,height: 12)
                }
                .padding(.horizontal, 10)
            }
        }
        .sheet(isPresented: $alertVisible,onDismiss: {
            OptionRow.selected.removeAll()
        }){
            if isIntroduce == true {
                EditIntroduceView(data: $content)
            }
            else {
                EditDetailProfileView(title: title, data: $content)
            }
        }
        .padding(5)
        .onTapGesture {
            if title == "거주지" || title == "키" || title == "취미" || title == "성격" {
                alertVisible = true
            }
            else if title == "소개" {
                alertVisible = true
            }
        }
    }
}

struct NotEditableRow : View {
    
    @State var title : String
    @Binding var content : String
    
    var body: some View {
        HStack{
            Text(title)
                .font(.system(size:18))
                .foregroundColor(Colors.grey500)
                .frame(width: 80)
            Spacer()
            ZStack{
                HStack{
                    Spacer()
                    Text(content)
                        .font(.system(size:18))
                        .foregroundColor(Colors.grey700)
                        .frame(width: 200)
                    Spacer()
                }
            }
        }
        .padding(5)
    }
    
}
