//
//  CafeteriaImageCell.swift
//  IOS
//
//  Created by 김세현 on 2020/09/07.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct CafeteriaImageCell: View {
    
    @State var imageUrl : String
    @State var uiImage = UIImage()
    
    var body: some View {
        ZStack{
            Image(uiImage: uiImage)
                .resizable()
                .frame(width: UIScreen.main.bounds.width/3 * 0.9, height: UIScreen.main.bounds.width/3 * 0.9)
        }
        .onAppear(){
            if self.imageUrl != "" {
                ImageManager.shared.loadImage(url: self.imageUrl){ uiImage in
                    self.uiImage=uiImage
                }
            }
        }
    }
}
