//
//  ConditionRow.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct EditConditionRow: View {
    
    @Binding var index : Int
    @Binding var changeAlertVisible : Bool
    @Binding var value : String
    
    @State var title : String
   
    
    @State var conditionVisible = false
    
    var body: some View {
        ZStack{
            VStack(spacing: 5){
                HStack{
                    Spacer()
                    Text(title)
                        .font(.system(size: 17))
                        .foregroundColor(Colors.grey500)
                        .frame(width: 50)
                    Spacer()
                    Text(value)
                        .font(.system(size: 17))
                        .foregroundColor(Colors.grey700)
                        .frame(width: 200)
                    Spacer()
                }
                Text("")
                    .frame(width: UIScreen.main.bounds.width*0.9,height: 2)
                    .background(Colors.grey300)
            }
            HStack{
                Spacer()
                Image("more_icon")
                    .padding(.trailing,30)
            }
        }
        .onTapGesture {
            if self.index == 1 {
                self.conditionVisible=true
            }
            else {
                self.changeAlertVisible=true
            }
        }
        .sheet(isPresented: self.$conditionVisible){
            ConditionView(value: self.$value, title: self.title)
        }
    }
}
