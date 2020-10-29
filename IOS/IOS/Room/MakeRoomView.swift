//
//  MakeRoomView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/25.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MakeRoomView: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var title = ""
    @State var isActive = false
    
    init() {
        UITextView.appearance().backgroundColor = .clear
    }
    
    var body: some View {
        VStack{
            HStack{
                HStack{
                    Button(action: {
                        presentationMode.wrappedValue.dismiss()
                    }, label: {
                        Text("취소")
                            .font(.system(size: 20))
                            .foregroundColor(Color.white)
                            .padding(.leading, 20)
                    })
                    Spacer()
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40, alignment: .leading)
                
                HStack{
                    Text("방만들기")
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
                
                HStack{
                    Spacer()
                    NavigationLink(destination: SecondStep(title: $title),isActive: $isActive, label: {
                    })
                    Button(action: {
                        isActive = true
                    }, label: {
                        Text("다음")
                            .font(.system(size: 20))
                            .foregroundColor(title.count == 0 ? Colors.grey500 : Color.white)
                    })
                    .disabled(title.count == 0 ? true : false)
                    .padding(.trailing, 20)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
            }
            .padding()
            
            VStack(spacing: 30){
                Text("오픈채팅방 이름을 입력해 주세요.")
                    .font(.system(size: 20))
                    .foregroundColor(Color.white)
                
                VStack(spacing: 2){
                    TextEditor(text: $title)
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 20)
                        .multilineTextAlignment(.center)
                    Text("")
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 1)
                        .background(Color.white)
                }
            }
            
            Spacer()
        }
        .background(Color.black.opacity(0.5))
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(edges: .top)
        
    }
}

struct SecondStep: View {
    
    @Binding var title : String
    @State var category = "선택하기"
    
    @State var selected = false
    @State var actionSheetVisible = false
    @State var isActive = false
    
    init(title: Binding<String>) {
        UITextView.appearance().backgroundColor = .clear
        
        self._title = title
    }
    
    var body: some View {
        VStack{
            HStack{
                HStack{
                    NavigationLink(destination: MakeRoomView(), label: {
                        Text("뒤로")
                            .font(.system(size: 20))
                            .foregroundColor(Color.white)
                            .padding(.leading, 20)
                    })
                    Spacer()
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40, alignment: .leading)
                
                HStack{
                    Text("방만들기")
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
                
                HStack{
                    Spacer()
                    NavigationLink(destination: ThirdStep(title: $title, category: $category),isActive: $isActive, label: {
                    })
                    Button(action: {
                        isActive = true
                    }, label: {
                        Text("다음")
                            .font(.system(size: 20))
                            .foregroundColor(selected == false ? Colors.grey500 : Color.white)
                    })
                    .disabled(selected == false ? true : false)
                    .padding(.trailing, 20)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
            }
            .padding()
            
            VStack(spacing: 30){
                Text("카테고리를 선택해주세요.")
                    .font(.system(size: 20))
                    .foregroundColor(Color.white)
                
                VStack(spacing: 2){
                    Button(action: {
                        actionSheetVisible = true
                    }, label: {
                        Text(category)
                            .font(.system(size: 20))
                            .foregroundColor(selected == false ? Colors.grey500 : Color.white)
                    })
                    .padding(.trailing, 20)
                    
                    Text("")
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 1)
                        .background(Color.white)
                        .actionSheet(isPresented: $actionSheetVisible){
                            ActionSheet(title: Text("카테고리를 선택해주세요.")
                                        , buttons: [.default(Text("취미")){
                                            category = "취미"
                                            selected = true
                                        }, .default(Text("스터디")){
                                            category = "스터디"
                                            selected = true
                                        }, .default(Text("고민상담")){
                                            category = "고민상담"
                                            selected = true
                                        }, .default(Text("잡담")){
                                            category = "잡담"
                                            selected = true
                                        }])
                        }
                }
            }
            
            Spacer()
        }
        .background(Color.black.opacity(0.5))
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(.all)
    }
}

struct ThirdStep: View {
    
    @Binding var title : String
    @Binding var category : String
    @State var introduce = ""
    
    
    @State var isActive = false
    @State var room = RoomData(room_id: "", room_title: "", maker: "", category: "", room_date: "'", room_introduce: "", univ_name: "")
    
    init(title: Binding<String>, category: Binding<String>) {
        UITextView.appearance().backgroundColor = .clear
        
        self._title = title
        self._category = category
    }
    
    var body: some View {
        VStack{
            HStack{
                HStack{
                    NavigationLink(destination: SecondStep(title: $title), label: {
                        Text("뒤로")
                            .font(.system(size: 20))
                            .foregroundColor(Color.white)
                            .padding(.leading, 20)
                    })
                    Spacer()
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40, alignment: .leading)
                
                HStack{
                    Text("방만들기")
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
                
                HStack{
                    Spacer()
                    NavigationLink(destination: ChatView(room: room),isActive: $isActive, label: {
                    })
                    Button(action: {
                        var roomId = ""
                        for i in 0..<12 {
                            roomId += "\(Int.random(in: 0..<10))"
                        }
                        
                        let now = Date()
                        let dateFormatter = DateFormatter()
                        dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
                        let date=dateFormatter.string(from: now)
                        
                        AlamofireService.shared.createRoom(roomId: roomId, title: title, category: category, date: date, introduce: introduce, univName: UserInfo.shared.UNIV, userId: UserInfo.shared.ID){ result in
                            
                            room = RoomData(room_id: roomId, room_title: title, maker: UserInfo.shared.ID, category: category, room_date: date, room_introduce: introduce, univ_name: UserInfo.shared.UNIV)
                            
                            isActive = true
                        }
                    }, label: {
                        Text("만들기")
                            .font(.system(size: 20))
                            .foregroundColor(introduce.count == 0 ? Colors.grey500 : Color.white)
                    })
                    .disabled(introduce.count == 0 ? true : false)
                    .padding(.trailing, 20)
                }
                .frame(width: UIScreen.main.bounds.width/3, height: 40)
            }
            .padding()
            
            VStack(spacing: 30){
                Text("방소개를 입력해주세요")
                    .font(.system(size: 20))
                    .foregroundColor(Color.white)
                
                VStack(spacing: 2){
                    TextEditor(text: $introduce)
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 20)
                        .multilineTextAlignment(.center)
                    Text("")
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 1)
                        .background(Color.white)
                }
            }
            Spacer()
        }
        .background(Color.black.opacity(0.5))
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(.all)
    }
}
