//
//  MenuRow.swift
//  IOS
//
//  Created by 김세현 on 2020/09/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct MenuRow: View {
    
    @State var menu : Menu
    
    var body: some View {
        HStack{
            Text(menu.menu_title!)
                .font(.system(size: 20))
                .foregroundColor(Colors.grey500)
            Spacer()
            Text("\(menu.menu_price!)")
                .font(.system(size: 20))
                .foregroundColor(Colors.grey500)
        }
    }
}

