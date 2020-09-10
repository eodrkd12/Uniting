//
//  DetailView.swift
//  IOS
//
//  Created by 김세현 on 2020/09/03.
//  Copyright © 2020 KSH. All rights reserved.
//

import SwiftUI
import NMapsMap
import SwiftSoup

struct DetailView: View {
    @Environment(\.presentationMode) var presentationMode
    
    @State var mapView = NMFMapView(frame: CGRect(x: 0, y: 0, width: 200, height: 200))
    @State var cafeteria : CafeteriaData
    @State var showMoreTimes = false
    @State var moreTimes : [String.SubSequence] = []
    @State var firstTime : String.SubSequence = ""
    
    @State var menuList : [Menu] = []
    @State var imageList : [String] = []
    
    @State var rows:Int = 0
    @State var total:Int = 0
    
    @State var reviewList : [ReviewData] = []
    @State var reviewInit = false
    
    var body: some View {
        ScrollView{
            VStack(spacing: 20){
                HStack{
                    Spacer()
                    MapView(cgRect: CGRect(x: 0, y: 0, width: UIScreen.main.bounds.width*0.95, height: 250), x: cafeteria.x, y: cafeteria.y)
                        .frame(width: UIScreen.main.bounds.width*0.95, height: 250)
                        .padding(.top,65)
                    Spacer()
                }
                VStack(spacing: 10){
                    HStack{
                        Text(cafeteria.name)
                            .font(.system(size: 30))
                            .foregroundColor(Colors.grey900)
                        Spacer()
                    }
                    HStack(spacing: 10){
                        Image("call_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                        
                        if cafeteria.phone == "" {
                            Text("등록된 정보가 없습니다.")
                        }
                        else {
                            Button(action: {
                                var tel = "tel://\(self.cafeteria.phone)"
                                guard let url = URL(string: tel) else { return }
                                if #available(iOS 10, *) {
                                    UIApplication.shared.open(url)
                                }
                                else {
                                    UIApplication.shared.openURL(url)
                                }
                            }, label: {
                                Text(cafeteria.phone)
                            })
                        }
                        
                        Spacer()
                    }
                    HStack(spacing: 10){
                        Image("marker_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                        Text(cafeteria.roadAddr)
                        Spacer()
                    }
                    HStack(alignment: .top, spacing: 10){
                        Image("time_icon")
                            .resizable()
                            .frame(width: 20, height: 20)
                        
                        if moreTimes.count == 0 {
                            
                            Text("등록된 정보가 없습니다.")
                        }
                        else {
                            HStack(alignment: .top, spacing: 5){
                                VStack{
                                    Text(firstTime)
                                    if showMoreTimes {
                                        ForEach(moreTimes, id: \.self){ time in
                                            Text(time)
                                        }
                                    }
                                }
                                if moreTimes.count > 1 {
                                    Image("more_icon")
                                        .resizable()
                                        .frame(width: 15, height: 15)
                                        .onTapGesture {
                                            self.showMoreTimes.toggle()
                                    }
                                    .padding(.top,3)
                                }
                            }
                        }
                        Spacer()
                    }
                }
                .padding(.leading, 10)
                VStack(spacing: 2){
                    ForEach(0..<self.rows, id: \.self){i in
                        HStack(spacing:2){
                            Spacer()
                            CafeteriaImageCell(imageUrl: self.imageList[i*3])
                            if i*3+1<self.total {
                                CafeteriaImageCell(imageUrl: self.imageList[i*3+1])
                                if i*3+2<self.total {
                                    ZStack{
                                        CafeteriaImageCell(imageUrl: self.imageList[i*3+2])
                                    }
                                    .frame(width: UIScreen.main.bounds.width/3 * 0.9, height: UIScreen.main.bounds.width/3 * 0.9)
                                }
                                else{
                                    CafeteriaImageCell(imageUrl: "")
                                }
                            }
                            else{
                                CafeteriaImageCell(imageUrl: "")
                                CafeteriaImageCell(imageUrl: "")
                            }
                            Spacer()
                        }
                    }
                }
                
                VStack(spacing: 20){
                    HStack{
                        Text("메뉴")
                            .font(.system(size: 25))
                            .foregroundColor(Colors.grey900)
                        Spacer()
                    }
                    .padding(.leading, 10)
                    VStack(spacing: 10){
                        ForEach(menuList, id: \.name){ menu in
                            MenuRow(menu: menu)
                        }
                    }
                    .padding(.horizontal, 15)
                }
                
                VStack(spacing: 20){
                    Text("평가를 남겨주세요.")
                        .font(.system(size: 25))
                    
                    ReviewStarPoint(editable: true)
                    
                }
                
                VStack(spacing: 20){
                    HStack{
                        Text("리뷰")
                            .font(.system(size: 25))
                            .foregroundColor(Colors.grey900)
                        Spacer()
                        Text("더보기")
                            .font(.system(size: 20))
                            .foregroundColor(Colors.grey500)
                    }
                    .padding(.horizontal, 10)
                    VStack(spacing: 10){
                        EmptyView()
                    }
                    .padding(.horizontal, 15)
                }
            }
            VStack(spacing: 10){
                if reviewList.count > 0 {
                    ReviewItem(reviewData: self.reviewList[0])
                }
                if reviewList.count > 1 {
                    ReviewItem(reviewData: self.reviewList[1])
                }
                if reviewList.count > 2 {
                    ReviewItem(reviewData: self.reviewList[2])
                }
            }
            .padding(.horizontal, 15)
            Spacer()
        }
        .edgesIgnoringSafeArea(.top)
        .navigationBarTitle("")
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(
            leading: Image("black_back_icon")
                .onTapGesture {
                    self.presentationMode.wrappedValue.dismiss()
            }
        )
            .onAppear(){
                print(self.cafeteria.name)
                let bizHourInfo = self.cafeteria.bizHourInfo.replacingOccurrences(of: " ", with: "")
                self.moreTimes = bizHourInfo.split(separator: "|")
                if self.moreTimes.count > 0 {
                    self.firstTime = self.moreTimes[0]
                    self.moreTimes.remove(at: 0)
                }
                
                guard let menuUrl = URL(string: "https://store.naver.com/restaurants/detail?id=\(self.cafeteria.id)") else {return}
                guard let imageUrl = URL(string: "https://store.naver.com/restaurants/detail?id=\(self.cafeteria.id)&tab=photo") else {return}
                
                let menuHtml = try! String(contentsOf: menuUrl, encoding: .utf8)
                let imageHtml = try! String(contentsOf: imageUrl, encoding: .utf8)
                
                let menuDoc = try! SwiftSoup.parseBodyFragment(menuHtml)
                let menuData = try! menuDoc.body()?.select("ul[class=list_menu]").select("li")
                menuData?.forEach({ (element) in
                    let name = try! element.select("li span[class=name]").text()
                    let price = try! element.select("li em[class=price").text()
                    
                    self.menuList.append(Menu(name: name, price: price))
                })
                
                let imageDoc = try! SwiftSoup.parseBodyFragment(imageHtml)
                let imageData = try! imageDoc.body()?.select("div[class=flick_content").select("div[class=thumb]")
                imageData?.forEach({ (element) in
                    let imageUrl = try! element.select("img").attr("src")
                    self.imageList.append(imageUrl)
                })
                
                self.total=self.imageList.count
                self.rows=self.imageList.count/3
                if self.imageList.count%3 != 0 {
                    self.rows+=1
                }
                if self.imageList.count > 2 {
                    self.rows = 2
                }
                
                AlamofireService.shared.getReview(cafeteriaName: self.cafeteria.name) { (reviewList) in
                    self.reviewList = reviewList
                    self.reviewInit = true
                }
        }
    }
}

