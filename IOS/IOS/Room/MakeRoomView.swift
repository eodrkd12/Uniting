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
    
    @State static var title = ""
    @State static var category = ""
    @State static var introduce = ""
    
    @State var content = ""
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
                    NavigationLink(destination: SecondStep(), label: {
                        Text("다음")
                            .font(.system(size: 20))
                            .foregroundColor(content.count == 0 ? Colors.grey500 : Color.white)
                    })
                    .disabled(content.count == 0 ? true : false)
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
                    TextEditor(text: $content)
                        .font(.system(size: 20))
                        .foregroundColor(Color.white)
                        .frame(width: UIScreen.main.bounds.width * 0.85, height: 20)
                        .multilineTextAlignment(.center)
                        .onChange(of: content){ value in
                            MakeRoomView.title = content
                        }
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
    
    @State var selected = false
    @State var category = "선택하기"
    @State var actionSheetVisible = false
    init() {
        UITextView.appearance().backgroundColor = .clear
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
                    NavigationLink(destination: ThirdStep(), label: {
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
                                            MakeRoomView.category = "취미"
                                            selected = true
                                        }, .default(Text("스터디")){
                                            category = "스터디"
                                            MakeRoomView.category = "스터디"
                                            selected = true
                                        }, .default(Text("고민상담")){
                                            category = "고민상담"
                                            MakeRoomView.category = "고민상담"
                                            selected = true
                                        }, .default(Text("잡담")){
                                            category = "잡담"
                                            MakeRoomView.category = "잡담"
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
    
    @State var introduce = ""
    
    init() {
        UITextView.appearance().backgroundColor = .clear
    }
    
    var body: some View {
        VStack{
            HStack{
                HStack{
                    NavigationLink(destination: SecondStep(), label: {
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
                    NavigationLink(destination: ThirdStep(), label: {
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
                        .onChange(of: introduce){ value in
                            MakeRoomView.introduce = introduce
                        }
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
