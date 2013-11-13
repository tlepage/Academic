//
//  HappinessViewController.m
//  Happiness
//
//  Created by Tom LePage on 7/16/12.
//  Copyright (c) 2012 Cash America Intl. All rights reserved.
//

#import "HappinessViewController.h"
#import "FaceView.h"

@interface HappinessViewController () <FaceViewDataSource> // privately implement

@property (nonatomic, weak) IBOutlet FaceView *faceView;
@property (nonatomic, weak) IBOutlet UIToolbar *toolBar;
@end

@implementation HappinessViewController

@synthesize happiness = _happiness;
@synthesize faceView = _faceView;
@synthesize splitViewBarButtonItem = _splitViewBarButtonItem;
@synthesize toolBar = _toolBar;

- (void)setSplitViewBarButtonItem:(UIBarButtonItem *)splitViewBarButtonItem
{
    if (_splitViewBarButtonItem != splitViewBarButtonItem)
    {
        NSMutableArray *toolBarItems = [self.toolBar.items mutableCopy];
        
        if (_splitViewBarButtonItem)
        {
            [toolBarItems removeObject:_splitViewBarButtonItem];
        }
        
        if (splitViewBarButtonItem)
        {
            [toolBarItems insertObject:splitViewBarButtonItem atIndex:0];
        }
    }
}

- (void) setHappiness:(int)happiness
{
    _happiness = happiness;
    [self.faceView setNeedsDisplay]; 
}

- (void) setFaceView:(FaceView *)faceView
{
    _faceView = faceView;
    [self.faceView addGestureRecognizer:[[UIPinchGestureRecognizer alloc] initWithTarget:self.faceView 
                                                                                  action:@selector(pinch:)]];
    // target must be self because the view can't see the model
    [self.faceView addGestureRecognizer:[[UIPanGestureRecognizer alloc] initWithTarget:self 
                                                                                action:@selector(handleHappinessGesture:)]];
    
    // set ourself as the dataSource delegate
    self.faceView.dataSource = self;
    
}

- (void) handleHappinessGesture:(UIPanGestureRecognizer *)gesture
{
    if ((gesture.state == UIGestureRecognizerStateChanged) ||
        (gesture.state == UIGestureRecognizerStateEnded))
    {
        // translation still needs to happen in faceView's coordinate space
        CGPoint translation = [gesture translationInView:self.faceView];
        self.happiness -= translation.y * 0.5; // make it less sensitive
        
        [gesture setTranslation:CGPointZero inView:self.faceView];
    }
}

- (float) smileForFaceView:(FaceView *)sender
{
    return (self.happiness - 50) / 50.0;
}

- (BOOL) shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)toInterfaceOrientation
{
    return YES;
}

- (void)viewDidUnload {
    [self setToolBar:nil];
    [super viewDidUnload];
}
@end
