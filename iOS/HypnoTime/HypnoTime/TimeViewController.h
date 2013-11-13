//
//  TimeViewController.h
//  HypnoTime
//
//  Created by Tom LePage on 9/26/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TimeViewController : UIViewController
{
    __weak IBOutlet UILabel *timeLabel;
}

- (IBAction)showCurrentTime:(id)sender;
- (void)spinTimeLabel;
- (void)bounceTimeLabel;

@end
