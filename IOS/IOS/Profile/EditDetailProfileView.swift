//
//  EditDetailProfileView.swift
//  IOS
//
//  Created by 김세현 on 2020/10/28.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditDetailProfileView: View {
    
    @Environment(\.presentationMode) var presentationMode
    
    @State var title : String
    @Binding var data : String
    
    @State var optionRowArray : [OptionRow] = []
    
    var cityArray = ["서울","경기","인천","대전","대구","부산","울산","광주","강원","세종","충북","충남","경북","전남","제주","해외"]
    var heightArray = 150...200
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
                ForEach(optionRowArray, id: \.id){ (option) in
                    option
                }
            }
            .padding(.horizontal,30)
            .onAppear(){
                UITableView.appearance().separatorStyle = .none
            }
            Button(action: {
                data = ""
                OptionRow.selected.forEach { (option) in
                    data += "\(option.text),"
                }
                data.removeLast()
                presentationMode.wrappedValue.dismiss()
                OptionRow.selected.removeAll()
            }, label: {
                Text("확인")
                    .frame(width: UIScreen.main.bounds.width * 0.8)
                    .padding(5)
                    .font(.system(size: 30))
                    .foregroundColor(Color.white)
                    .background(Colors.primary)
                    .cornerRadius(30)
            })
        }
        .padding()
        .onAppear(){
            var id = 0
            if self.title == "거주지" {
                self.title = "거주지를 선택해주세요"
                self.cityArray.forEach { city in
                    self.optionRowArray.append(OptionRow(id: id, text: city, singleSelect: true))
                    id += 1
                }
            }
            else if self.title == "키" {
                self.title = "키를 선택해주세요"
                self.heightArray.forEach { i in
                    self.optionRowArray.append(OptionRow(id: id, text: "\(i)cm", singleSelect: true))
                    id += 1
                }
            }
            else if self.title == "취미" {
                self.title = "취미를 선택해주세요"
                self.hobbyArray.forEach { hobby in
                    self.optionRowArray.append(OptionRow(id: id, text: hobby))
                    id += 1
                }
            }
            else if self.title == "성격" {
                self.title = "성격을 선택해주세요"
                self.personalityArray.forEach { personality in
                    self.optionRowArray.append(OptionRow(id: id, text: personality))
                    id += 1
                }
            }
        }
    }
}

struct OptionRow: View {
    
    static var selected : [OptionRow] = []
    
    @State var id : Int
    @State var text : String
    @State var didTap = false
    @State var singleSelect = false
    @State var alertVisible = false
    
    var body: some View {
        HStack{
            Text(text)
                .font(.system(size: 25))
                .foregroundColor(didTap ? Colors.primary : Colors.grey300)
                .onTapGesture {
                    if self.singleSelect == true && OptionRow.selected.count>0 && self.didTap==false {
                        self.alertVisible = true
                    }
                    else{
                        self.didTap.toggle()
                        if self.didTap == true {
                            OptionRow.selected.append(self)
                        }
                        else {
                            for i in 0..<OptionRow.selected.count {
                                if OptionRow.selected[i].id == self.id {
                                    OptionRow.selected.remove(at: i)
                                    break
                                }
                            }
                        }
                    }
            }
            Spacer()
        }
        .alert(isPresented: self.$alertVisible){
            Alert(title: Text("한가지만 고를 수 있습니다"))
        }
    }
}

