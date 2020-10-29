//
//  PageView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/01.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI

struct PageView: View {
    
    @State var image : Image
    @State var title : String
    @State var subTitle : String
    
    var body: some View {
        ZStack{
            image
            VStack(spacing: 10){
                Text(title)
                    .font(.system(size: 30))
                    .foregroundColor(Colors.grey700)
                Text(subTitle)
                    .font(.system(size: 15))
                    .foregroundColor(Colors.grey500)
                Spacer()
            }
        }
    }
}
