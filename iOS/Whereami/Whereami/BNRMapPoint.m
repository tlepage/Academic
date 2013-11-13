//
//  BNRMapPoint.m
//  Whereami
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "BNRMapPoint.h"

@implementation BNRMapPoint

@synthesize coordinate = _coordinate, title = _title;

- (id)initWithCoordinate:(CLLocationCoordinate2D)c title:(NSString *)t
{
    self = [super init];
    
    if (self)
    {
        _coordinate = c;
        [self setTitle:t];
    }
    
    return self;
}

- (id)init
{
    return [self initWithCoordinate:CLLocationCoordinate2DMake(43.07, -89.32) title:@"Hometown"];
}

@end
