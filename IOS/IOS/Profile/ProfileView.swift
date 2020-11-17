//
//  ProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/14.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ProfileView: View {
    
    @Environment(\.presentationMode) var presentaionMode : Binding<PresentationMode>
    
    @Binding var profile : ProfileData
    
    @State var age = 0
    
    @State var hobby : [String.SubSequence] = []
    @State var personality : [String.SubSequence] = []
    
    @State var isCreated = false
    @State var room = RoomData(room_id: "", room_title: "", maker: "", category: "", room_date: "", room_introduce: "", univ_name: "")
    
    var body: some View {
        ScrollView{
            ZStack{
                VStack{
                    HStack{
                        Spacer()
                        VStack{
                            HStack{
                                Text("채팅시 비방 및 욕설행위를 하면 어플사용이 제한될 수 있습니다.")
                                    .font(.system(size: 15, weight: .bold))
                                    .foregroundColor(Color.white)
                                    .multilineTextAlignment(.center)
                                    .frame(width: UIScreen.main.bounds.width * 0.7)
                            }
                            Spacer()
                        }
                        .padding(.top,38)
                        Spacer()
                    }
                    .frame(height: 120)
                    .background(Colors.primary)
                    
                    VStack{
                        HStack{
                            Text("프로필")
                                .font(.system(size: 20))
                                .padding(.leading, 10)
                            Spacer()
                        }
                        VStack(spacing: 10){
                            HStack{
                                Text("기본 정보")
                                    .font(.system(size: 13))
                                    .foregroundColor(Colors.grey500)
                                    .padding(.leading,15)
                                Spacer()
                            }
                            ProfileRow(title: "키",value: profile.user_height ?? "정보없음")
                            ProfileRow(title: "거주지", value: profile.user_city)
                            ProfileRow(title: "학과", value: profile.dept_name)
                        }
                        .padding()
                        .frame(width: UIScreen.main.bounds.width*0.9)
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(color: Color.black.opacity(0.3),
                                radius: 2,
                                x: 3,
                                y: 3)
                        
                        VStack(spacing: 10){
                            HStack{
                                Text("자기소개")
                                    .font(.system(size: 13))
                                    .foregroundColor(Colors.grey500)
                                    .padding(.leading,15)
                                Spacer()
                            }
                            HStack{
                                Text(profile.user_introduce == nil ? "등록된 소개가 없습니다." : profile.user_introduce!)
                                    .font(.system(size: 15))
                                    .foregroundColor(Colors.grey900)
                                    .padding(.horizontal, 10)
                                Spacer()
                            }
                        }
                        .padding()
                        .frame(width: UIScreen.main.bounds.width*0.9)
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(color: Color.black.opacity(0.3),
                                radius: 2,
                                x: 3,
                                y: 3)
                        
                        VStack(spacing: 10){
                            HStack{
                                Text("취미")
                                    .font(.system(size: 13))
                                    .foregroundColor(Colors.grey500)
                                    .padding(.leading,15)
                                Spacer()
                            }
                            if hobby.count > 0 {
                                DynamicHeightHStack(items: hobby)
                            }
                        }
                        .padding()
                        .frame(width: UIScreen.main.bounds.width*0.9)
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(color: Color.black.opacity(0.3),
                                radius: 2,
                                x: 3,
                                y: 3)
                        
                        VStack(spacing: 10){
                            HStack{
                                Text("성격")
                                    .font(.system(size: 13))
                                    .foregroundColor(Colors.grey500)
                                    .padding(.leading,15)
                                Spacer()
                            }
                            if personality.count > 0 {
                                DynamicHeightHStack(items: personality)
                            }
                        }
                        .padding()
                        .frame(width: UIScreen.main.bounds.width*0.9)
                        .background(Color.white)
                        .cornerRadius(10)
                        .shadow(color: Color.black.opacity(0.3),
                                radius: 2,
                                x: 3,
                                y: 3)
                        
                        NavigationLink(destination: ChatView(room: room), isActive: $isCreated, label: {})
                        Button(action:{
                            var roomId = ""
                            for i in 0..<12 {
                                roomId += "\(Int.random(in: 0..<10))"
                            }
                            
                            let now = Date()
                            let dateFormatter = DateFormatter()
                            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                            let date=dateFormatter.string(from: now)
                            
                            var title = "\(UserInfo.shared.ID)&\(profile.user_id)"
                            var category = "데이팅"
                            var introduce = ""
                            
                            AlamofireService.shared.createRoom(roomId: roomId, title: title, category: category, date: date, introduce: introduce, univName: UserInfo.shared.UNIV, userId: UserInfo.shared.ID){ result in
                                
                                room = RoomData(room_id: roomId, room_title: title, maker: UserInfo.shared.ID, category: category, room_date: date, room_introduce: introduce, univ_name: UserInfo.shared.UNIV)
                                
                                AlamofireService.shared.insertChatHistory(userId: UserInfo.shared.ID, partnerId: profile.user_id, date: date){ result in
                                    isCreated = true
                                }
                            }
                        }, label: {
                            Image("chat_button")
                                .resizable()
                                .frame(width: 150, height: 70)
                                .shadow(color: Color.black.opacity(0.3),
                                        radius: 2,
                                        x: 3,
                                        y: 3)
                        })
                        Spacer()
                    }
                    .padding(.top,40)
                    .padding()
                    
                    
                }
                
                VStack{
                    HStack{
                        VStack(spacing: 10){
                            Text("닉네임")
                                .font(.system(size: 13))
                                .foregroundColor(Colors.grey500)
                            Text("\(profile.user_nickname)")
                                .font(.system(size: 18))
                                .foregroundColor(Colors.grey900)
                        }
                        .frame(width: UIScreen.main.bounds.width*0.3)
                        Spacer()
                        VStack(spacing: 10){
                            if(age != 0){
                                Text("나이")
                                    .font(.system(size: 13))
                                    .foregroundColor(Colors.grey500)
                                Text("\(age)세")
                                    .font(.system(size: 18))
                                    .foregroundColor(Colors.grey900)
                            }
                        }
                        .frame(width: UIScreen.main.bounds.width*0.3)
                    }
                    .padding(20)
                    .background(Color.white)
                    .cornerRadius(10)
                    .padding(.top, 80)
                    .frame(width:UIScreen.main.bounds.width*0.8)
                    .shadow(color: Color.black.opacity(0.3),
                            radius: 2,
                            x: 3,
                            y: 3)
                    Spacer()
                }
                
                VStack{
                    HStack{
                        Button(action: {
                            presentaionMode.wrappedValue.dismiss()
                        }, label: {
                            Image("white_back_icon")
                                .resizable()
                                .frame(width: 20, height: 20)
                                .padding()
                                .padding(.top, 22)
                        })
                        Spacer()
                    }
                    Spacer()
                    
                }
            }
        }
        .onAppear(){
            var now = Date()
            let dateFormatter = DateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
            let date=dateFormatter.string(from: now)
            
            var year=self.profile.user_birthday.split(separator: "-")[0]
            
            self.age=(Int(date.split(separator: "-")[0]).unsafelyUnwrapped-Int(year).unsafelyUnwrapped+1)
            
            if profile.user_hobby != nil {
                self.hobby = (profile.user_hobby?.split(separator: ","))!
            }
            if profile.user_personality != nil {
                self.personality = (profile.user_personality?.split(separator: ","))!
            }
        }
        .ignoresSafeArea(.all)
        .navigationTitle("")
        .navigationBarHidden(true)
    }
}
