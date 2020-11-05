//
//  CafeteriaList.swift
//  IOS
//
//  Created by 김세현 on 2020/09/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct PreviewList: View {
    
    @State var type : String
    @State var previewList : [CafeteriaPreview]
    
    var body: some View {
        VStack{
            HStack{
                Text(type)
                    .font(.system(size: 20))
                    .foregroundColor(Colors.grey700)
                Spacer()
                NavigationLink(destination: MoreCafeteriaView(type: type)) {
                    Text("더보기")
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                }
            }
            ScrollView(.horizontal,showsIndicators: false, content: {
                HStack(spacing: 15){
                    ForEach(previewList.count > 10 ? 0..<10 : 0..<previewList.count, id:\.self){ i in
                        PreviewItem(preview: previewList[i])
                    }
                }
            })
        }
        .padding()
    }
}

struct PreviewItem: View {
    
    @State var preview : CafeteriaPreview
    @State var uiImage = UIImage()
    
    @State var isActive = false
    
    @State var cafeteria : Cafeteria? = nil
    
    var body: some View{
        ZStack{
            NavigationLink(destination: CafeInformView(cafeNo: preview.cafe_no), isActive: $isActive, label: {})
            VStack(spacing: 10){
                if preview.cafe_thumbnail != nil && preview.cafe_thumbnail != "" {
                    Image(uiImage: uiImage)
                        .resizable()
                        .frame(width: 150, height: 150)
                        .cornerRadius(15)
                }
                else {
                    Text("이미지가 없습니다")
                        .font(.system(size: 18))
                        .foregroundColor(Colors.grey500)
                        .frame(width: 150, height: 150)
                }
                Text(preview.cafe_name)
                    .font(.system(size: 18))
                    .foregroundColor(Colors.grey700)
                    .lineLimit(1)
                    .truncationMode(.tail)
                    .frame(width: 150)
                HStack(spacing: 8){
                    Image(systemName: "star.fill")
                        .foregroundColor(Color.yellow)
                        .padding(.leading, 10)
                    Text(preview.star_point == nil ? "0" : String(preview.star_point!))
                        .font(.system(size: 15))
                    Spacer()
                }
                .frame(width: 150)
            }
            .onTapGesture {
                isActive = true
            }
            
        }
        .padding(5)
        .onAppear(){
            print(self.preview)
            if self.preview.cafe_thumbnail != "" && self.preview.cafe_thumbnail != nil {
                
                ImageManager.shared.loadImage(url: self.preview.cafe_thumbnail!){ uiImage in
                    self.uiImage=uiImage
                }
            }
        }
    }
}
