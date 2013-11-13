//
//  JSONSerializable.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/9/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@protocol JSONSerializable <NSObject>

- (void)readFromJSONDictionary:(NSDictionary *)d;

@end
