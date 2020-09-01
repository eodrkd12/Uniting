//
//  ConditionRow.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ConditionRow: View {
    
    static var selected : [ConditionRow] = []
    
    @State var id : Int
    @State var text : String
    @State var didTap = false
    @State var scopeSelect = true
    @State var alertVisible = false
    
    var body: some View {
        HStack{
            Text(text)
                .font(.system(size: 25))
                .foregroundColor(didTap ? Colors.primary : Colors.grey300)
                .onTapGesture {
                    if self.scopeSelect == true && ConditionRow.selected.count>1 && self.didTap==false {
                        self.alertVisible = true
                    }
                    else{
                        self.didTap.toggle()
                        if self.didTap == true {
                            ConditionRow.selected.append(self)
                        }
                        else {
                            for i in 0..<ConditionRow.selected.count {
                                if ConditionRow.selected[i].id == self.id {
                                    ConditionRow.selected.remove(at: i)
                                    break
                                }
                            }
                        }
                    }
            }
            Spacer()
        }
        .alert(isPresented: self.$alertVisible){
            Alert(title: Text("두개까지 고를 수 있습니다"))
        }
    }
}

