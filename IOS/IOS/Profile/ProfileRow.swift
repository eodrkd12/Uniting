//
//  ProfileRow.swift
//  IOS
//
//  Created by 김세현 on 2020/10/05.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct ProfileRow: View {
    
    @State var title : String
    @State var value : String
    
    var body: some View {
        HStack{
            VStack{
                Text("\(title)")
                    .font(.system(size: 18))
                    .foregroundColor(Colors.grey900)
            }
            .frame(width: UIScreen.main.bounds.width * 0.40)
            VStack{
                Text("\(value)")
                    .font(.system(size: 18))
                    .foregroundColor(Colors.grey900)
            }
            .frame(width: UIScreen.main.bounds.width * 0.40)
            
        }
    }
}
