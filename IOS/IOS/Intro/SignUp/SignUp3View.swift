//
//  SignUp3View.swift
//  IOS
//
//  Created by 김세현 on 2020/11/06.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUp3View: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var university : String
    @State var department : String
    @State var webmail : String
    
    @State var id = ""
    @State var idCheck = false
    @State var pw = ""
    @State var pwConfirm = ""
    @State var nickname = ""
    @State var birthday = "선택"
    @State var city = "선택"
    @State var gender = ""
    @State var enterYear = "선택"
    
    @State var datePickerVisible = false
    @State var date = Date()
    
    @State var alertVisible = false
    @State var alertMessage = ""
    
    @State var cityVisible = false
    @State var enterYearVisible = false
    var dateFormatter : DateFormatter {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        
        return formatter
    }
    
    @State var activeNext = false
    
    @State var checkedId = ""
    
    var body: some View {
        ZStack{
            NavigationLink(
                destination: SignUp4View(id: checkedId, pw: pw),
                isActive: $activeNext,
                label: {})
            ScrollView{
                HStack{
                    Button(action: {
                        if idCheck == true {
                            AlamofireService.shared.idDelete(id: checkedId){ result in
                                
                            }
                        }
                        presentationMode.wrappedValue.dismiss()
                    }, label: {
                        Text("뒤로")
                            .foregroundColor(Colors.primary)
                    })
                    Spacer()
                }
                HStack{
                    Text("STEP 3 OF 4")
                    Spacer()
                }
                .padding()
                VStack(spacing: 10){
                    
                    VStack{
                        HStack{
                            Text("아이디")
                                .font(.system(size: 20))
                            Spacer()
                        }
                        
                        ZStack{
                            TextField("영문, 숫자 5~20자", text: $id)
                                .frame(height:30)
                                .onChange(of: id, perform: { value in
                                    if idCheck == true {
                                        AlamofireService.shared.idDelete(id: checkedId){ result in
                                            idCheck = false
                                        }
                                    }
                                })
                                .padding(.leading,5)
                            HStack{
                                Spacer()
                                Button(action: {
                                    if id.count > 4 && id.count < 21 {
                                        AlamofireService.shared.idCheck(id: id){ count in
                                            switch count.count {
                                            case 0:
                                                AlamofireService.shared.idInsert(id: id){ result in
                                                    if result.result == "success" {
                                                        idCheck = true
                                                        checkedId = id
                                                        alertMessage = "사용가능한 아이디입니다."
                                                        alertVisible = true
                                                    }
                                                }
                                                break
                                            case 1:
                                                alertMessage = "중복된 아이디입니다."
                                                alertVisible = true
                                                break
                                            default: break
                                            }
                                        }
                                    }
                                    else {
                                        alertMessage = "아이디를 확인해주세요."
                                        alertVisible = true
                                    }
                                }, label: {
                                    Text("중복확인")
                                        .padding(5)
                                        .foregroundColor(Color.white)
                                        .background(Colors.primary)
                                        .cornerRadius(15)
                                })
                                .alert(isPresented: $alertVisible){
                                    Alert(title: Text(alertMessage))
                             
                                }
                            }
                            .padding(5)
                        }
                        Line(width: 2)
                    }
                    
                    VStack{
                        HStack{
                            Text("비밀번호")
                                .font(.system(size: 20))
                            Spacer()
                        }
                        
                        SecureField("비밀번호", text: $pw)
                            .frame(height:30)
                            .padding(.leading,5)
                            
                        Line(width: 2)
                    }
                    
                    VStack{
                        HStack{
                            Text("비밀번호 확인")
                                .font(.system(size: 20))
                            Spacer()
                        }
                        
                        SecureField("비밀번호 확인", text: $pwConfirm)
                            .frame(height:30)
                            .padding(.leading,5)
                        Line(width: 2)
                    }
                    
                    VStack{
                        HStack{
                            Text("닉네임")
                                .font(.system(size: 20))
                            Spacer()
                        }
                        
                        TextField("3~10자", text: $nickname)
                            .frame(height:30)
                            .padding(.leading,5)
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
                                cityVisible = true
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
                            .sheet(isPresented: $cityVisible){
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
                    .padding()
                    
                    VStack{
                        HStack{
                            Text("학교 정보")
                                .font(.system(size: 20))
                            Spacer()
                        }
                        .padding(.horizontal)
                        .padding(.top)
                        .padding(.bottom,5)
                        Line(width: 2)
                        VStack{
                            HStack{
                                Text("입학년도")
                                    .font(.system(size: 20))
                                Spacer()
                            }
                            
                            ZStack{
                                Button(action: {
                                    enterYearVisible = true
                                }, label: {
                                    HStack{
                                        Text(enterYear)
                                            .font(.system(size:20))
                                            .foregroundColor(Colors.grey500)
                                        Spacer()
                                        Image("more_icon")
                                            .resizable()
                                            .frame(width: 12,height: 12)
                                    }
                                    .padding(.horizontal)
                                })
                                .sheet(isPresented: $enterYearVisible){
                                    EditDetailProfileView(title: "입학년도", data: $enterYear)
                                }
                            }
                            Line(width: 2)
                        }
                        .padding(.horizontal)
                        .padding(.vertical,5)
                        
                        VStack{
                            HStack{
                                Text("학교 학과")
                                    .font(.system(size: 20))
                                Spacer()
                            }
                            
                            HStack{
                                Text("\(university) \(department)")
                                    .font(.system(size:20))
                                    .foregroundColor(Colors.grey500)
                                Spacer()
                            }
                            .padding(.horizontal)
                            Line(width: 2)
                        }
                        .padding(.horizontal)
                        .padding(.vertical,5)
                        
                        VStack{
                            HStack{
                                Text("웹메일")
                                    .font(.system(size: 20))
                                Spacer()
                            }
                            
                            HStack{
                                Text(webmail)
                                    .font(.system(size:20))
                                    .foregroundColor(Colors.grey500)
                                Spacer()
                            }
                            .padding(.horizontal)
                            Line(width: 2)
                        }
                        .padding(.horizontal)
                        .padding(.vertical,5)

                    }
                    .overlay(RoundedRectangle(cornerRadius: 15).stroke(Colors.grey500,lineWidth: 2))
                    .padding()
                    
                    Button(action: {
                        if id.count == 0 {
                            alertMessage = "아이디를 입력해주세요."
                            alertVisible = true
                        }
                        else if idCheck == false {
                            alertMessage = "아이디 중복확인을 해주세요."
                            alertVisible = true
                        }
                        else if pw.count == 0 {
                            alertMessage = "비밀번호를 입력해주세요."
                            alertVisible = true
                        }
                        else if pwConfirm.count == 0 {
                            alertMessage = "비밀번호 확인란을 입력해주세요."
                            alertVisible = true
                        }
                        else if pw != pwConfirm {
                            alertMessage = "비밀번호를 확인해주세요."
                            alertVisible = true
                        }
                        else if nickname.count == 0 {
                            alertMessage = "닉네임을 입력해주세요."
                            alertVisible = true
                        }
                        else if birthday == "선택" {
                            alertMessage = "생년월일을 입력해주세요."
                            alertVisible = true
                        }
                        else if city == "선택" {
                            alertMessage = "거주지를 입력해주세요."
                            alertVisible = true
                        }
                        else if gender == "" {
                            alertMessage = "성별을 선택해주세요."
                            alertVisible = true
                        }
                        else if enterYear == "선택" {
                            alertMessage = "입학년도를 선택해주세요."
                            alertVisible = true
                        }
                        else {
                            AlamofireService.shared.signUp(userId: id, userPw: pw, userNickname: nickname, userBirthday: birthday, userCity: city, userGender: gender, univName: university, deptName: department, webMail: webmail, enterYear: enterYear){ result in
                                if result.result == "success" {
                                    activeNext = true
                                }
                            }
                        }
                    }, label: {
                        Text("다음")
                            .font(.system(size: 25))
                            .foregroundColor(Color.white)
                            .padding(5)
                            .frame(width: UIScreen.main.bounds.width * 0.8)
                            .background(Colors.primary)
                            .cornerRadius(45)
                    })
                    .alert(isPresented: $alertVisible, content: {
                        Alert(title: Text(alertMessage))
                    })
                }
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
        .padding(.top, 10)
    }
}

struct SignUp3View_Previews: PreviewProvider {
    static var previews: some View {
        SignUp3View(university: "", department: "", webmail: "")
    }
}

