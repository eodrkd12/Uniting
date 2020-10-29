//
//  Line.swift
//  IOS
//
//  Created by 김세현 on 2020/10/27.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct Line: View {
    
    @State var width : CGFloat
    
    var body: some View {
        HStack{
            Text("")
            Spacer()
        }
        .frame(height: width)
        .background(Colors.grey200)
    }
}

