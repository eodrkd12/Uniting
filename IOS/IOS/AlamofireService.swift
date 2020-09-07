//
//  Alamofire.swift
//  IOS
//
//  Created by 김세현 on 2020/09/02.
//  Copyright © 2020 KSH. All rights reserved.
//

import Alamofire
import SwiftyJSON

class AlamofireService : ObservableObject {
    
    func getCafeteriaList(start: Int, display: Int, query: String, sortingOrder: String, callback: @escaping ([CafeteriaData]) -> Void){
        
        let url = "https://store.naver.com/sogum/api/businesses"
        
        let body = [
        "start" : start,
        "display" : display,
        "query" : query,
        "sortingOrder" : sortingOrder
        ] as [String : Any]
        
        AF.request(
            url,
            method: .get,
            parameters: body
        ).responseData { (data) in
            let json = try! JSON(data: data.data!)
            
            var items : [CafeteriaData] = []
            
            json["items"].arrayValue.forEach { (json) in
                
                var tags : [String] = []
                
                var temp = json["tags"].arrayObject
                
                temp?.forEach({ (tag) in
                    tags.append(tag as! String)
                })
                
                var item = CafeteriaData(id: json["id"].stringValue, name: json["name"].stringValue, x: json["x"].stringValue, y: json["y"].stringValue, phone: json["phone"].stringValue, imageSrc: json["imageSrc"].stringValue, roadAddr: json["roadAddr"].stringValue, tags: tags, options: json["options"].stringValue, bizHourInfo: json["bizHourInfo"].stringValue)
                
                items.append(item)
            }
            
            callback(items)
        }
    }
}
