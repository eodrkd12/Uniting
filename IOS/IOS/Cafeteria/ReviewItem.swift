//
//  ReviewItem.swift
//  IOS
//
//  Created by 김세현 on 2020/09/08.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct ReviewItem: View {
    
    var review : Review
    
    var date : String = ""
    
    @State var uiImage = UIImage()
    
    var body: some View {
        VStack(spacing: 10){
            HStack{
                VStack{
                    HStack{
                        Text(review.user_nickname)
                            .font(.system(size: 15))
                            .foregroundColor(Colors.grey700)
                        Spacer()
                    }
                    HStack{
                        ReviewStarPoint(point: review.review_point, editable: false)
                        
                        Text("날짜")
                            .font(.system(size: 12))
                            .foregroundColor(Colors.grey500)
                        
                        Spacer()
                    }
                    Spacer()
                }
                Spacer()
                VStack{
                    Button(action: {
                        
                    }, label: {
                        Text("삭제")
                            .font(.system(size: 12))
                    })
                        .foregroundColor(Color.red)
                }
            }
            
            Image(uiImage: uiImage)
            .resizable()
                .frame(width: UIScreen.main.bounds.width * 0.9, height: UIScreen.main.bounds.width * 0.9)
            
            HStack{
                Text(review.review_content)
                    .font(.system(size: 15))
                    .foregroundColor(Colors.grey700)
                Spacer()
            }
        }
        .onAppear(){
            if self.review.image_url != nil && self.review.image_url != "" {
                ImageManager.shared.loadImage(url: self.review.image_url){ uiImage in
                    self.uiImage=uiImage
                }
            }
        }
    }
}
