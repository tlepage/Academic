//
//  RSSChanel.h
//  Nerdfeed
//
//  Created by Tom LePage on 10/5/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "JSONSerializable.h"

@interface RSSChannel : NSObject <NSXMLParserDelegate, JSONSerializable, NSCoding, NSCopying>
{
    NSMutableString *currentString;
}

@property (nonatomic, weak) id parentParserDelegate;

@property (nonatomic, strong) NSString *title;
@property (nonatomic, strong) NSString *infoString;
@property (nonatomic, readonly, strong) NSMutableArray *items;

- (void)trimItemTitles;
- (void)addItemsFromChannel:(RSSChannel *)otherChannel;

@end
