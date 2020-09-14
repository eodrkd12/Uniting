//
//  CafeteriaList.swift
//  IOS
//
//  Created by 김세현 on 2020/09/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import class Kingfisher.KingfisherManager

struct CafeteriaList: View {
    
    @State var type : String
    @State var cafeteriaList : [CafeteriaData]
    
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
                    ForEach(cafeteriaList, id:\.id){ cafeteria in
                        CafeteriaItem(cafeteria: cafeteria)
                    }
                }
            })
        }
        .padding()
    }
}

struct CafeteriaItem: View {
    
    @State var cafeteria : CafeteriaData
    @State var uiImage = UIImage()
    
    var body: some View{
        ZStack{
            VStack(spacing: 10){
                if cafeteria.imageSrc != "" {
                    Image(uiImage: uiImage)
                        .resizable()
                        .frame(width: 150, height: 150)
                        .cornerRadius(15)
                }
                else {
                    Text("이미지가 없습니다")
                        .font(.system(size: 15))
                        .foregroundColor(Colors.grey500)
                        .frame(width: 150, height: 150)
                }
                Text(cafeteria.name)
                    .font(.system(size: 15))
                    .foregroundColor(Colors.grey700)
                    .frame(width: 150)
                HStack{
                    Image(systemName: "star.fill")
                        .foregroundColor(Color.yellow)
                    Spacer()
                }
            }
            NavigationLink(destination: DetailView(cafeteria: cafeteria)) {
                Text("")
                    .frame(width: 150,height: 180)
            }
        }
        .padding(5)
        .onAppear(){
            if self.cafeteria.imageSrc != "" {
                
                ImageManager.shared.loadImage(url: self.cafeteria.imageSrc){ uiImage in
                    self.uiImage=uiImage
                }
            }
        }
    }
}
