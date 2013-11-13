//
//  TouchDrawView.h
//  TouchTracker
//
//  Created by Tom LePage on 10/3/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@class Line;

@interface TouchDrawView : UIView <UIGestureRecognizerDelegate>
{
    NSMutableArray *completeLines;
    NSMutableDictionary *linesInProcess;
    
    UIPanGestureRecognizer *moveRecognizer;
}

@property (nonatomic, weak) Line *selectedLine;

- (Line *)lineAtPoint:(CGPoint)p;

- (void)clearAll;
- (void)endTouches:(NSSet *)touches;

@end
