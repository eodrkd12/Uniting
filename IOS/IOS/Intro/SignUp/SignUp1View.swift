//
//  SignUp1View.swift
//  IOS
//
//  Created by 김세현 on 2020/11/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct SignUp1View: View {
    
    @Environment(\.presentationMode) var presentationMode: Binding<PresentationMode>
    
    @State var university = ""
    @State var department = ""
    
    @State var universityList : [UniversityData] = []
    @State var departmentList : [DepartmentData] = []
    
    @State var selectedUniversity = ""
    @State var selectedUniversityMail = ""
    @State var selectedDepartment = ""
    
    @State var alertVisible = false
    @State var activeNext = false
    
    var body: some View {
        VStack(spacing: 10){
            NavigationLink(destination: SignUp2View(university: selectedUniversity, universityMail: selectedUniversityMail, department: selectedDepartment), isActive: $activeNext, label: {})
            
            HStack{
                Button(action: {
                    presentationMode.wrappedValue.dismiss()
                }, label: {
                    Text("뒤로")
                        .foregroundColor(Colors.primary)
                })
                Spacer()
            }
            
            HStack{
                Text("STEP 1 OF 4")
                Spacer()
            }
            .padding()
            
            VStack{
                HStack{
                    Text("학교를 입력해주세요.")
                        .font(.system(size: 25))
                    Spacer()
                }
                GeometryReader{ g in
                    VStack{
                        TextField("",text: $university)
                            .onChange(of: university){ value in
                                selectedUniversity = ""
                                selectedDepartment = ""
                            }
                            .frame(width: g.size.width, height: 30)
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                            .multilineTextAlignment(.leading)
                            .padding(.leading,5)
                        
                    }
                }
                .frame(height: 30)
                Line(width: 2)
                if selectedUniversity == "" {
                    VStack{
                        ForEach(universityList, id: \.univ_name){ universityData in
                            if universityData.univ_name.contains(university) {
                                HStack{
                                    Button(action: {
                                        AlamofireService.shared.getDepartment(university: universityData.univ_name){ departmentList in
                                            self.departmentList = departmentList
                                            university = universityData.univ_name
                                            selectedUniversityMail = universityData.univ_mail
                                            alertVisible = true
                                        }
                                    }, label: {
                                        Text(universityData.univ_name)
                                            .font(.system(size: 20))
                                            .foregroundColor(Colors.grey500)
                                            .padding(.top, 5)
                                            .padding(.leading, 10)
                                    })
                                    Spacer()
                                }
                            }
                        }
                    }
                    .alert(isPresented: $alertVisible){
                        Alert.init(title: Text("선택한 학교가 맞습니까?"),
                                   message: Text(university),
                                   primaryButton: Alert.Button.default(Text("확인"), action: {
                                    selectedUniversity = university
                                   }),
                                   secondaryButton: Alert.Button.cancel())
                    }
                }
            }
            .padding()
            
            VStack{
                HStack{
                    Text("학과를 입력해주세요.")
                        .font(.system(size: 25))
                    Spacer()
                }
                GeometryReader{ g in
                    VStack{
                        TextField("", text: $department)
                            .onChange(of: department){ value in
                                selectedDepartment = ""
                            }
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey700)
                            .multilineTextAlignment(.leading)
                            .frame(width: g.size.width, height: 30)
                            .padding(.leading,5)
                    }
                }
                .frame(height: 30)
                Line(width: 2)
                if selectedDepartment == "" {
                    VStack{
                        ForEach(departmentList, id: \.dept_name){ departmentData in
                            if departmentData.dept_name.contains(department) {
                                HStack{
                                    Button(action: {
                                        department = departmentData.dept_name
                                        alertVisible = true
                                    }, label: {
                                        Text(departmentData.dept_name)
                                            .font(.system(size: 20))
                                            .foregroundColor(Colors.grey500)
                                            .padding(.top, 5)
                                            .padding(.leading, 10)
                                    })
                                    Spacer()
                                }
                            }
                        }
                    }
                    .alert(isPresented: $alertVisible){
                        Alert.init(title: Text("선택한 학과가 맞습니까?"),
                                   message: Text(department),
                                   primaryButton: Alert.Button.default(Text("확인"), action: {
                                    selectedDepartment = department
                                   }),
                                   secondaryButton: Alert.Button.cancel())
                    }
                }
            }
            .padding()
            
            Button(action: {
                if selectedDepartment != "" {
                    activeNext = true
                }
            }, label: {
                Text("다음")
                    .font(.system(size: 25))
                    .foregroundColor(Color.white)
                    .padding(5)
                    .frame(width: UIScreen.main.bounds.width * 0.8)
                    .background(selectedDepartment == "" ? Colors.grey500 : Colors.primary)
                    .cornerRadius(45)
            })
            
            
            Spacer()
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .ignoresSafeArea(.all)
        .padding(.top, 10)
        .padding(.horizontal, 20)
        .onAppear(){
            AlamofireService.shared.getUniversity(){ universityList in
                self.universityList = universityList
            }
        }
    }
}

struct SignUp1View_Previews: PreviewProvider {
    static var previews: some View {
        SignUp1View()
    }
}
