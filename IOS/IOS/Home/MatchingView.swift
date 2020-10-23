//
//  MatchingView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MatchingView: View {
    
    @State var index = 0
    @State var changeAlertVisible = false
    @State var height = ""
    @State var age = ""
    @State var department = ""
    @State var hobby = ""
    @State var personality = ""
    @State var alertVisible = false
    @State var profileVisible = false
    @State var noMatching = false
    
    @State var profile = ProfileData(user_id: "", user_nickname: "", user_birthday: "", dept_name: "", user_gender: "", enter_year: "", user_city: "", user_hobby: "", user_personality: "", user_introduce: "", user_height: "", user_signdate: "", univ_name: "", user_email: "")
    
    var body: some View {
        ZStack{
            VStack(spacing: 10){
                PagingView(index: self.$index.animation(), maxIndex: 1){
                    PageView(image: Image("test"), title: "일반 매칭", subTitle: "유니팅에 가입한 상대방과 매칭")
                    PageView(image: Image("test"), title: "스마트 매칭", subTitle: "유니팅에 가입한 상대방 스마트하게 매칭")
                }
                .aspectRatio(4/4, contentMode: .fit)
                .clipShape(RoundedRectangle(cornerRadius: 10))
                .frame(width: UIScreen.main.bounds.width, height: 280)
                if index == 1 {
                    VStack(spacing: 10){
                        HStack{
                            Text("매칭조건")
                                .font(.system(size: 17))
                                .foregroundColor(Colors.grey700)
                                .padding(.leading,30)
                            Spacer()
                        }
                        EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, value: self.$height, title: "키")
                        EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, value: self.$age, title: "나이")
                        EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, value: self.$department, title: "학과")
                        EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, value: self.$hobby, title: "취미")
                        EditConditionRow(index: self.$index, changeAlertVisible: self.$changeAlertVisible, value: self.$personality, title: "성격")
                    }
                }
                NavigationLink(destination: ProfileView(profile: $profile), isActive: $profileVisible, label: {
                    
                })
                Button(action: {
                    if self.index == 0 {
                        AlamofireService.shared.randomMatching(){(profileList) in
                            if profileList.count == 0 {
                                noMatching = true
                            }
                            else {
                                self.profile = profileList[0]
                                self.profileVisible = true
                            }
                        }
                    }
                    else {
                        if self.height == "" || self.age == ""  {
                            self.alertVisible = true
                        }
                        else {
                            AlamofireService.shared.smartMatching(height: self.height, age: self.age, department: self.department, hobby: self.hobby, personality: self.personality){ (profileList) in
                                if profileList.count == 0 {
                                    noMatching = true
                                }
                                else {
                                    self.profile = profileList[0]
                                    self.profileVisible = true
                                }
                            }
                        }
                    }
                }, label: {
                    Image("big_main_connect_button")
                        .resizable()
                        .frame(width: 270, height: 45)
                        .alert(isPresented: self.$alertVisible){
                            Alert(title: Text("키와 나이는 필수항목입니다."))
                        }
                        .alert(isPresented: self.$noMatching){
                            Alert(title: Text("매칭할 수 있는 사람이 없습니다."))
                        }
                })
                
                
                if changeAlertVisible {
                    GeometryReader{_ in
                        ChangeAlert(showing: self.$changeAlertVisible, index: self.$index)
                    }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
                }
                
                Spacer()
            }
        }
    }
}
