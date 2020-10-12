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
                            
                        },
                        label: {
                            Text("저장")
                                .font(.system(size: 20))
                                .padding()
                        })
                    }
                    .frame(width: UIScreen.main.bounds.width/3, height: 40)
                }
                
                VStack(spacing: 5){
                    HStack{
                        Text("사용자 정보")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                    }
                    .padding(.leading, 10)
                    .padding(.top, 10)
                    HStack{
                        Text("")
                        Spacer()
                    }
                    .frame(height: 2)
                    .background(Colors.grey300)
                    
                    VStack(){
                        HStack{
                            Text("닉네임")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    TextEditor(text: $nickname)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200, height: 35)
                                    /*
                                     Text(nickname)
                                     .font(.system(size:18))
                                     .foregroundColor(Colors.grey700)
                                     .frame(width: 200)
                                     */
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                    .background(Colors.grey300)
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
                        .padding(5)
                        
                        HStack{
                            Text("생년월일")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Button(action: {
                                        datePickerVisible = true
                                    }, label: {
                                        Text(birthday)
                                            .font(.system(size:18))
                                            .foregroundColor(Colors.grey700)
                                            .frame(width: 200)
                                    })
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                    .background(Colors.grey300)
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
                        .padding(5)
                        
                        HStack{
                            Text("거주지")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Text(city)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200)
                                    
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                    .background(Colors.grey300)
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
                        .padding(5)
                        
                        HStack{
                            Text("가입일자")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Text(signdate)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200)
                                    
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                }
                            }
                        }
                        .padding(5)
                    }
                }
                .overlay(
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(Colors.grey300, lineWidth: 2)
                )
                .padding()
                .padding(.top,30)
                
                
                
                VStack(spacing: 5){
                    HStack{
                        Text("학교 정보")
                            .font(.system(size: 22))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                    }
                    .padding(.leading, 10)
                    .padding(.top, 10)
                    HStack{
                        Text("")
                        Spacer()
                    }
                    .frame(height: 2)
                    .background(Colors.grey300)
                    VStack{
                        HStack{
                            Text("학교")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Text(university)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200)
                                    
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                }
                            }
                        }
                        .padding(5)
                        
                        HStack{
                            Text("학과")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Text(department)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200)
                                    
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                }
                            }
                        }
                        .padding(5)
                        
                        HStack{
                            Text("웹메일")
                                .font(.system(size:18))
                                .foregroundColor(Colors.grey500)
                                .frame(width: 80)
                            Spacer()
                            ZStack{
                                VStack{
                                    Text(webmail)
                                        .font(.system(size:18))
                                        .foregroundColor(Colors.grey700)
                                        .frame(width: 200)
                                    
                                    HStack{
                                        Text("")
                                        Spacer()
                                    }
                                    .frame(height: 2)
                                }
                            }
                        }
                        .padding(5)
                    }
                }
                .overlay(
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(Colors.grey300, lineWidth: 2)
                )
                .padding()
                .padding(.top,30)
                
                Spacer()
            }
            
            if datePickerVisible {
                GeometryReader{_ in
                    DatePicker("", selection: $date, displayedComponents: .date)
                }.background(Color.black.opacity(0.5).edgesIgnoringSafeArea(.all))
            }
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .onAppear(){
            AlamofireService.shared.getProfile(userId: UserInfo.shared.ID){ profile in
                self.nickname = profile.user_nickname
                self.birthday = profile.user_birthday
                self.city = profile.user_city
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
