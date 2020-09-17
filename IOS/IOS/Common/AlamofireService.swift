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
    
    static var shared = AlamofireService()
    
    let serverUrl = "http://52.78.27.41:1901"
    
    // 홈
    func randomMatching(callback: @escaping ([ProfileData]) -> Void){
        
        let url = "\(serverUrl)/common/sql/select"
        
        var sql = "SELECT * FROM user WHERE (user_id NOT IN (SELECT user_id FROM chathistory WHERE partner_id = '\(UserInfo.shared.ID)')) "
        sql += "AND (user_id NOT IN (SELECT partner_id FROM chathistory WHERE user_id='\(UserInfo.shared.ID)')) "
        sql += "AND (user_id <> '\(UserInfo.shared.ID)') "
        sql += "AND (user_gender <> '\(UserInfo.shared.GENDER)') "
        sql += "AND (univ_name = '\(UserInfo.shared.UNIV)') "
        if UserInfo.shared.BLOCKINGDEPT == 0 {
            sql += "AND (user_id NOT IN (SELECT user_id FROM blocking WHERE blocking = '\(UserInfo.shared.DEPT)')) "
        }
        sql += "ORDER BY RAND() LIMIT 1"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseData { (data) in
            do {
                let json = try! JSON(data: data.data!)
                print(json)
            }
        }
    }
    
    func smartMatching(height: String, age: String, department: String, hobby: String, personality: String, callback: @escaping ([ProfileData]) -> Void){
        let url = "\(serverUrl)/common/sql/select"
        
        var minHeight = height.split(separator: "~")[0]
        minHeight.removeLast()
        var maxHeight = height.split(separator: "~")[1]
        minHeight.removeFirst()
        
        var minAge = age.split(separator: "~")[0]
        minAge.removeLast()
        var maxAge = age.split(separator: "~")[1]
        maxAge.removeFirst()
        
        var formatter = DateFormatter()
        formatter.dateFormat = "yyyy"
        var year = formatter.string(from: Date())
        
        var minYear = Int(year)! - Int(String(minAge))!
        var maxYear = Int(year)! - Int(String(maxAge))!
        
        var sql = "SELECT * FROM user "
        sql += "WHERE (user_id NOT IN (SELECT user_id FROM chathistory WHERE partner_id = '\(UserInfo.shared.ID)')) "
        sql += "AND (user_id NOT IN (SELECT partner_id FROM chathistory WHERE user_id = '\(UserInfo.shared.ID)')) "
        sql += "AND (user_gender <> '\(UserInfo.shared.GENDER)') "
        sql += "AND (user_id <> '\(UserInfo.shared.ID)') "
        sql += "AND (user_height >= '\(minHeight)') "
        sql += "AND (user_height <= '\(maxHeight)') "
        sql += "AND (user_birthday <= '\(minYear)-12-31') "
        sql += "AND (user_birthday >= '\(maxYear)-01-01') "
        
        if department != "" {
            sql += "AND ("
            var departmentList = department.split(separator: ",")
            departmentList.forEach { (subStr) in
                
            }
        }
    }
    
    // 식당
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
            do {
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
            } catch {
                self.getCafeteriaList(start: start, display: display, query: query, sortingOrder: sortingOrder, callback: callback)
            }
        }
    }
    
    func getReview(cafeteriaName: String, callback: @escaping ([ReviewData]) -> Void){
        
        let sql = "select * from review where cafe_name = '\(cafeteriaName)' order by review_date desc"
        
        AF.request(
            "\(serverUrl)/common/sql/select",
            method: .post,
            parameters : [
                "sql" : sql
            ]
        ).responseData { (response) in
            var reviewList = try! JSONDecoder().decode([ReviewData].self, from: response.data!)
            
            callback(reviewList)
        }
    }
    
    
}
