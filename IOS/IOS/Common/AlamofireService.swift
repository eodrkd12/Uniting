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
    
    // 회원가입
    func getUniversity(callback: @escaping ([UniversityData]) -> Void){
        let url = "\(serverUrl)/university"
        
        AF.request(
            url,
            method: .get
        ).responseJSON { res in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode([UniversityData].self, from: json)
                    
                    callback(result)
                    
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func getDepartment(university: String, callback: @escaping ([DepartmentData]) -> Void) {
        let url = "\(serverUrl)/common/sql/select"
        
        let sql = "SELECT dept_name FROM department WHERE univ_name = '\(university)'"
        
        AF.request(
            url,
            method: .post,
            parameters: [
                "sql" : sql
            ]
        ).responseJSON { res in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode([DepartmentData].self, from: json)
                    
                    callback(result)
                    
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
    // 로그인
    func login(userId: String, userPw: String, callback: @escaping (Int) -> Void){
        let url = "\(serverUrl)/common/sql/select/single"
        
        let sql = "SELECT user_pw as result FROM user WHERE user_id = '\(userId)'"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON { res in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    
                    var resultPw = result.result
                    
                    if userPw == resultPw {
                        callback(1)
                    }
                    else {
                        callback(2)
                    }
                } catch {
                    print(error)
                    callback(0)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
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
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let profile = try JSONDecoder().decode([ProfileData].self, from: json)
                    
                    callback(profile)
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func smartMatching(height: String, age: String, department: String, hobby: String, personality: String, callback: @escaping ([ProfileData]) -> Void){
        let url = "\(serverUrl)/common/sql/select"
        
        var minHeight = height.split(separator: "~")[0]
        minHeight.removeLast()
        var maxHeight = height.split(separator: "~")[1]
        maxHeight.removeFirst()
        
        var minAge = age.split(separator: "~")[0]
        minAge.removeLast()
        var maxAge = age.split(separator: "~")[1]
        maxAge.removeFirst()
        
        var formatter = DateFormatter()
        formatter.dateFormat = "yyyy"
        var year = formatter.string(from: Date())
        
        var minYear = Int(year)! - Int(String(minAge))! + 1
        var maxYear = Int(year)! - Int(String(maxAge))! + 1
        
        var sql = "SELECT * FROM user "
        sql += "WHERE (user_id NOT IN (SELECT user_id FROM chathistory WHERE partner_id = '\(UserInfo.shared.ID)')) "
        sql += "AND (user_id NOT IN (SELECT partner_id FROM chathistory WHERE user_id = '\(UserInfo.shared.ID)')) "
        sql += "AND (user_gender <> '\(UserInfo.shared.GENDER)') "
        sql += "AND (univ_name = '\(UserInfo.shared.UNIV)') "
        sql += "AND (user_id <> '\(UserInfo.shared.ID)') "
        sql += "AND (user_height >= '\(minHeight)') "
        sql += "AND (user_height <= '\(maxHeight)') "
        sql += "AND (user_birthday <= '\(minYear)-12-31') "
        sql += "AND (user_birthday >= '\(maxYear)-01-01') "
        
        if department != "" {
            sql += "AND ("
            var departmentList = department.split(separator: ",")
            departmentList.forEach { (subStr) in
                if subStr == departmentList[0] {
                    sql += "dept_name LIKE '\(subStr)' "
                }
                else {
                    var str = subStr
                    str.removeFirst()
                    sql += "OR dept_name LIKE '\(str)' "
                }
            }
            sql += ")"
        }
        
        if hobby != "" {
            sql += "AND ("
            var hobbyList = hobby.split(separator: ",")
            hobbyList.forEach{ (subStr) in
                if subStr == hobbyList[0] {
                    sql += "user_hobby LIKE '\(subStr)' "
                }
                else {
                    var str = subStr
                    str.removeFirst()
                    sql += "OR dept_name LIKE '\(str)' "
                }
            }
            sql += ")"
        }
        
        if personality != "" {
            sql += "AND ("
            var personalityList = personality.split(separator: ",")
            personalityList.forEach{ (subStr) in
                if subStr == personalityList[0] {
                    sql += "user_personality LIKE '\(subStr)' "
                }
                else {
                    var str = subStr
                    str.removeFirst()
                    sql += "OR user_personality LIKE '\(str)' "
                }
            }
            sql += ")"
        }
        sql += "ORDER BY RAND() LIMIT 1"
        
        
        var body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let profile = try JSONDecoder().decode([ProfileData].self, from: json)
                    
                    callback(profile)
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
    // 채팅방
    func insertChatHistory(userId: String, partnerId: String, date: String, callback: @escaping (ResultModel) -> Void){
        let url = "\(serverUrl)/common/sql/insert"
        
        var sql = "INSERT INTO chathistory"
        sql += "VALUES('\(userId)', '\(partnerId)', '\(date)')"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON{ (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    callback(result)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func createRoom(roomId: String, title: String, category: String, date: String, introduce: String, univName: String, userId: String, callback: @escaping (ResultModel) -> Void){
        
        let url = "\(serverUrl)/common/sql/insert"
        
        var sql = "INSERT INTO room "
        sql += "VALUES('\(roomId)','\(title)','\(category)','\(date)','\(introduce)','\(univName)','\(userId)')"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
           url,
           method: .post,
           parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    callback(result)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func exitRoom(roomId: String, userId: String,type: String, callback: @escaping (ResultModel) -> Void){
        var url = ""
        if type == "delete" {
            url = "\(serverUrl)/room/delete"
        }
        else {
            url = "\(serverUrl)/room/exit"
        }
        
        let body = [
            "room_id" : roomId,
            "user_id" : userId
        ]
        
        AF.request(
           url,
           method: .post,
           parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    callback(result)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    func getMyRoom(userId: String, callback: @escaping ([RoomData]) -> Void){
        let url = "\(serverUrl)/common/sql/select"
        
        var sql = "SELECT room.room_id AS room_id, room_title, category, room_date, room_introduce, univ_name, maker "
        sql += "FROM room, joined "
        sql += "WHERE room.room_id = joined.room_id AND user_id = '\(userId)'"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
           url,
           method: .post,
           parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let roomList = try JSONDecoder().decode([RoomData].self, from: json)
                    callback(roomList)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
            
        }
    }
    
    func getOpenRoom(univName: String, category: String, callback: @escaping ([RoomData]) -> Void){
        let url = "\(serverUrl)/common/sql/select"
        
        let sql = "SELECT * FROM room WHERE univ_name = '\(univName)' AND category = '\(category)'"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
           url,
           method: .post,
           parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let roomList = try JSONDecoder().decode([RoomData].self, from: json)
                    print(roomList)
                    callback(roomList)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
            
        }
    }
    
    func getMembers(roomId: String, callback: @escaping ([MemberData]) -> Void){
        let url = "\(serverUrl)/common/sql/select"
        
        let sql = "SELECT joined.user_id AS user_id, user_nickname FROM joined, user WHERE room_id = '\(roomId)' AND joined.user_id = user.user_id"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let memberList = try JSONDecoder().decode([MemberData].self, from: json)
                    
                    callback(memberList)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func insertChat(chat: ChatData, callback: @escaping (ResultModel) -> Void){
        let url = "\(serverUrl)/common/sql/insert"
        
        var sql = "INSERT INTO chat "
        sql += "VALUES('\(chat.chat_id)','\(chat.room_id)','\(chat.user_id)','\(chat.user_nickname)','\(chat.chat_content)','\(chat.chat_time)','\(chat.unread_member)','\(chat.system_chat)')"
        
        let body = [
            "sql" : sql
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    callback(result)
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func sendFcm(topic: String, title: String, content: String){
        let url = "\(serverUrl)/common/fcm"
        
        var body = [
            "topic" : topic,
            "title" : title,
            "content" : content
        ]
        
        AF.request(
            url,
            method: .post,
            parameters: body
        ).responseJSON { (res) in
            
        }
    }
    // 식당
    func getCafeteriaList(callback: @escaping (CafeteriaList) -> Void){
        let url = "\(serverUrl)/cafeteria/get/list"
        
        AF.request(
            url,
            method: .post,
            parameters: [
                "univ_name" : UserInfo.shared.UNIV
            ]
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let cafeteriaList = try JSONDecoder().decode(CafeteriaList.self, from: json)
                    
                    callback(cafeteriaList)
                    
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func getCafeteriaInform(cafeNo : Int, callback: @escaping (Cafeteria) -> Void){
        let url = "\(serverUrl)/cafeteria/get/inform"
        
        AF.request(
            url,
            method: .post,
            parameters: [
                "cafe_no" : cafeNo
            ]
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let cafeteria = try JSONDecoder().decode(Cafeteria.self, from: json)
                    
                    callback(cafeteria)
                    
                } catch {
                    print(error)
                }
            case .failure(let err):
                print(err)
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
    
    //프로필
    func getProfile(userId: String, callback: @escaping (ProfileData) -> Void){
        
        let url = "\(serverUrl)/common/sql/select/single"
        
        let sql = "SELECT * FROM user WHERE user_id='\(userId)'"
        
        AF.request(
            url,
            method: .post,
            parameters: [
                "sql" : sql
            ]
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let profile = try JSONDecoder().decode(ProfileData.self, from: json)
                    
                    callback(profile)
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
    
    func updateUserInfo(userId: String, nickname: String, birthday: String, city: String, height: String, hobby: String, personality: String, introduce: String, callback: @escaping (ResultModel) -> Void){
        let url = "\(serverUrl)/common/sql/insert"
        
        let sql = "UPDATE user SET user_nickname = '\(nickname)', user_birthday = '\(birthday)', user_city = '\(city)', user_height = '\(height)', user_hobby = '\(hobby)', user_personality='\(personality)', user_introduce='\(introduce)' WHERE user_id = '\(userId)'"
        
        AF.request(
            url,
            method: .post,
            parameters: [
                "sql" : sql
            ]
        ).responseJSON { (res) in
            switch res.result {
            case .success(let data):
                do {
                    let json = try JSONSerialization.data(withJSONObject: data, options: .prettyPrinted)
                    let result = try JSONDecoder().decode(ResultModel.self, from: json)
                    
                    callback(result)
                } catch {
                    print(error)
                }
                
            case .failure(let err):
                print(err)
            }
        }
    }
}
