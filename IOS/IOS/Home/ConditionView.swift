//
//  ConditionView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ConditionView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @Binding var value : String
    
    @State var title : String
    @State var conditionArray : [ConditionRow] = []
    @State var alertVisible = false
    
    var heightArray = 150...200
    var ageArray = 19...30
    var departmentArray = ["컴퓨터공학과","전자공학과"]
    var hobbyArray = ["운동","독서"]
    var personalityArray = ["착함","성실함"]
    
    var body: some View {
        VStack(spacing: 10){
            HStack{
                Text(title)
                    .font(.system(size: 30))
                    .foregroundColor(Colors.grey700)
                Spacer()
            }
            .padding(.horizontal,20)
            List{
                ForEach(conditionArray, id: \.id){ (condition) in
                    condition
                }
            }
            .padding(.horizontal,30)
            .onAppear(){
                UITableView.appearance().separatorStyle = .none
            }
            Image("save_button")
                .onTapGesture {
                    if self.title.contains("키") || self.title.contains("나이") {
                        if ConditionRow.selected.count<2 {
                            self.alertVisible=true
                        }
                        else {
                            var first = ConditionRow.selected[0].text
                            var second = ConditionRow.selected[1].text
                            
                            self.value = first < second ? "\(first) ~ \(second)" : "\(second) ~ \(first)"
                        }
                    }
                    else {
                        ConditionRow.selected.forEach { row in
                            self.value += row.text+", "
                        }
                        self.value.removeLast()
                        self.value.removeLast()
                    }
                    
                    ConditionRow.selected.removeAll()
                    self.presentationMode.wrappedValue.dismiss()
            }
        }
        .padding()
        .alert(isPresented: self.$alertVisible){
            Alert(title: Text("두개를 선택해주세요"))
        }
        .onAppear(){
            var id = 0
            
            if self.title == "키" {
                self.title = "키를 선택해주세요(두개 선택)"
                self.heightArray.forEach { i in
                    self.conditionArray.append(ConditionRow(id: id, text: "\(i)cm", scopeSelect: true))
                    id += 1
                }
            }
            else if self.title == "나이" {
                self.title = "나이를 선택해주세요(두개 선택)"
                self.ageArray.forEach { i in
                    self.conditionArray.append(ConditionRow(id: id, text: "\(i)", scopeSelect: true))
                    id += 1
                }
            }
            else if self.title == "학과" {
                self.title = "학과를 선택해주세요"
                self.departmentArray.forEach { department in
                    self.conditionArray.append(ConditionRow(id: id, text: department))
                    id += 1
                }
            }
            else if self.title == "취미" {
                self.title = "취미를 선택해주세요"
                self.hobbyArray.forEach { hobby in
                    self.conditionArray.append(ConditionRow(id: id, text: hobby))
                    id += 1
                }
            }
            else if self.title == "성격" {
                self.title = "성격을 선택해주세요"
                self.personalityArray.forEach { personality in
                    self.conditionArray.append(ConditionRow(id: id, text: personality))
                    id += 1
                }
            }
        }
    }
}
