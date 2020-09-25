//
//  Titlebar.swift
//  IOS
//
//  Created by 김세현 on 2020/09/24.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct Titlebar: View {
    
    @State var leading : ZStack
    @State var title : String
    @State var trailing : Image
    
    var body: some View {
        HStack{
            HStack{
                leading
            }
            .frame(width: UIScreen.main.bounds.width/3)
            HStack{
                Text(title)
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey700)
            }
            .frame(width: UIScreen.main.bounds.width/3)
            HStack{
                Spacer()
                trailing
            }
            .frame(width: UIScreen.main.bounds.width/3)
        }
    }
}
