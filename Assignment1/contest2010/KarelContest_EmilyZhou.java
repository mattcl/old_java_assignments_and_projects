package contest2010;

import stanford.karel.SuperKarel;

public class KarelContest_EmilyZhou extends SuperKarel {

    public void run() {
        PaintScreenBlack();
        Title();
        try {
            Thread.sleep(8500);
        } catch (InterruptedException ie) {
        }
        PaintScreenWhite();
        OpticalIllusion1();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {}
        PaintScreenBlack();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }
        turnAround();
        OpticalIllusion2();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
        PaintScreenBlack();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {
        }
        turnAround();
        OpticalIllusion3();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
        PaintScreenBlack();
    }

    private void Title(){
        MakeIO();
        MakeLP();
        MakeLT();
        MakeUI();
        MakeCS();
        MakeIA();
        MakeOL();
        MakeN();
    }

    private void MakeIO(){
        turnRight();
        for(int i=0;i<2;i++){
            MagentaLine();
        }
        OFirstLastLine();
        GreenLine();
        OLineMid();
        GreenLine();
        OFirstLastLine();
    }

    private void MakeLP(){
        for(int i=0;i<2;i++){
            MagentaLine();
        }
        MoveToCenter();
        for(int i1=0;i1<8;i1++){
            Magentamove();
        }
        for(int i1=0;i1<7;i1++){
            Greenmove();
        }
        paintCorner(GREEN);
        NextLineToRight();
        GreenLine();
        MoveToCenter();
        paintCorner(GREEN);
        for(int i1=0;i1<10;i1++){
            moveGreen();
        }
        for(int i1=0;i1<2;i1++){
            moveMagenta();
        }
        for(int i1=0;i1<3;i1++){
            moveGreen();
        }
        NextLineToRight();
        for(int i=0;i<1;i++){
            MoveToCenter();
            for(int i1=0;i1<6;i1++){
                Magentamove();
            }
            for(int i1=0;i1<9;i1++){
                Greenmove();
            }
            paintCorner(GREEN);
            NextLineToRight();
        }
    }

    private void MakeLT(){
        for(int i=0;i<2;i++){
            MagentaLine();
        }
        MoveToCenter();
        for(int i1=0;i1<8;i1++){
            Magentamove();
        }
        for(int i1=0;i1<7;i1++){
            Greenmove();
        }
        paintCorner(GREEN);
        NextLineToRight();
        for(int i=0;i<2;i++){
            GreenLine();
        }
        MoveToCenter();
        for(int i1=0;i1<8;i1++){
            Magentamove();
        }
        for(int i1=0;i1<7;i1++){
            Greenmove();
        }
        paintCorner(GREEN);
        NextLineToRight();
    }

    private void MakeUI(){
        MagentaLine();
        for(int i=0;i<2;i++){
            MoveToCenter();
            for(int i1=0;i1<4;i1++){
                Magentamove();
            }
            for(int i1=0;i1<11;i1++){
                Greenmove();
            }
            paintCorner(GREEN);
            NextLineToRight();
        }
        for(int i=0;i<2;i++){
            MagentaLine();
        }
    }

    private void MakeCS(){
        OFirstLastLine();
        GreenLine();
        MoveToCenter();
        for(int i=0;i<7;i++){
            Greenmove();
        }
        for(int i=0;i<4;i++){
            Magentamove();
        }
        for(int i=0;i<4;i++){
            Greenmove();
        }
        paintCorner(GREEN);
        NextLineToRight();
        MoveToCenter();
        for(int i=0;i<2;i++){
            Magentamove();
        }
        for(int i=0;i<4;i++){
            Greenmove();
        }
        for(int i=0;i<9;i++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        NextLineToRight();
        MoveToCenter();
        for(int i=0;i<8;i++){
            Magentamove();
        }
        for(int i=0;i<2;i++){
            Greenmove();
        }
        for(int i=0;i<5;i++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        NextLineToRight();
    }

    private void MakeIA(){
        MoveToCenter();
        for(int i1=0;i1<7;i1++){
            Magentamove();
        }
        for(int i1=0;i1<3;i1++){
            Greenmove();
        }
        for(int i1=0;i1<5;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        NextLineToRight();
        for(int i=0;i<2;i++){
            GreenLine();
        }
        for(int i=0;i<2;i++){
            MoveToCenter();
            for(int i1=0;i1<7;i1++){
                Magentamove();
            }
            Greenmove();
            for(int i1=0;i1<2;i1++){
                Magentamove();
            }
            for(int i1=0;i1<5;i1++){
                Greenmove();
            }
            paintCorner(GREEN);
            NextLineToRight();
        }
        for(int i1=0;i1<2;i1++){
            GreenLine();
        }
    }

    private void MakeOL(){
        OFirstLastLine2();
        MagentaLine();
        OLineMid2();
        MagentaLine();
        OFirstLastLine2();
        for(int i=0;i<2;i++){
            GreenLine();
        }
        MoveToCenter();
        paintCorner(GREEN);
        for(int i1=0;i1<8;i1++){
            moveGreen();
        }
        for(int i1=0;i1<7;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        NextLineToRight();
    }

    private void MakeN(){
        for(int i=0;i<2;i++){
            MagentaLine();
        }
        MoveToCenter();
        paintCorner(GREEN);
        for(int i1=0;i1<6;i1++){
            moveGreen();
        }
        for(int i1=0;i1<2;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        for(int i1=0;i1<7;i1++){
            moveGreen();
        }
        paintCorner(GREEN);
        NextLineToRight();
        MoveToCenter();
        paintCorner(GREEN);
        for(int i1=0;i1<5;i1++){
            moveGreen();
        }
        for(int i1=0;i1<2;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        for(int i1=0;i1<8;i1++){
            moveGreen();
        }
        paintCorner(GREEN);
        NextLineToRight();
        MagentaLine();
        MoveToCenter();
        for(int i1=0;i1<15;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
    }

    private void moveGreen(){
        move();
        paintCorner(GREEN);
    }

    private void Greenmove(){
        paintCorner(GREEN);
        move();

    }

    private void moveMagenta(){
        move();
        paintCorner(MAGENTA);
    }

    private void Magentamove(){
        paintCorner(MAGENTA);
        move();
    }

    private void GreenLine(){
        for(int i1=0;i1<15;i1++){
            move();
        }
        paintCorner(GREEN);
        for(int i1=0;i1<15;i1++){
            moveGreen();
        }
        NextLineToRight();
    }

    private void MagentaLine(){
        MoveToCenter();
        paintCorner(MAGENTA);
        for(int i1=0;i1<15;i1++){
            moveMagenta();
        }
        NextLineToRight();
    }

    private void OLineMid2(){
        MoveToCenter();
        for(int i1=0;i1<6;i1++){
            Magentamove();
        }
        paintCorner(GREEN);
        for(int i1=0;i1<3;i1++){
            moveGreen();
        }
        for(int i1=0;i1<6;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        NextLineToRight();
    }

    private void OLineMid(){
        MoveToCenter();
        paintCorner(GREEN);
        for(int i1=0;i1<6;i1++){
            moveGreen();
        }
        for(int i1=0;i1<3;i1++){
            Magentamove();
        }
        paintCorner(MAGENTA);
        for(int i1=0;i1<6;i1++){
            moveGreen();
        }
        paintCorner(GREEN);
        NextLineToRight();
    }

    private void OFirstLastLine2(){
        MoveToCenter();
        paintCorner(GREEN);
        for(int i=0;i<1;i++){
            moveGreen();
        }
        for(int i=0;i<13;i++){
            moveMagenta();
        }
        for(int i=0;i<1;i++){
            moveGreen();
        }
        NextLineToRight();
    }

    private void OFirstLastLine(){
        MoveToCenter();
        paintCorner(MAGENTA);
        for(int i=0;i<1;i++){
            moveMagenta();
        }
        for(int i=0;i<13;i++){
            moveGreen();
        }
        for(int i=0;i<1;i++){
            moveMagenta();
        }
        NextLineToRight();
    }

    private void MoveToCenter(){
        for(int i1=0;i1<15;i1++){
            move();
        }
    }

    private void NextLineToRight(){
        turnAround();
        moveToWall();
        turnLeft();
        move();
        turnLeft();
    }

    private void OpticalIllusion3() {
        for(int i=0;i<9;i++){
            OI3ThickLine();
        }
        WhiteLine();
        for(int i=0;i<3;i++){
            FourBlackOneWhiteLine();
        }
        OI3LastRow();
    }

    private void OI3LastRow(){
        for(int i=0;i<9;i++){
            OneWhite();
            FourBlack();
        }
        OneWhite();
        ThreeBlack();
        if(frontIsBlocked());
        paintCorner(BLACK);
    }

    private void OI3ThickLine(){
        WhiteLine();
        for(int i=0;i<4;i++){
            FourBlackOneWhiteLine();
        }
    }

    private void FourBlackOneWhiteLine(){
        for(int i=0;i<9;i++){
            OneWhite();
            FourBlack();
        }
        OneWhite();
        ThreeBlack();
        if(frontIsBlocked());
        paintCorner(BLACK);
        moveToNextRowfromBottom();
    }

    private void WhiteLine(){
        paintCorner(WHITE);
        while(frontIsClear()){
            move();
            paintCorner(WHITE);
        }
        moveToNextRowfromBottom();
    }

    private void OpticalIllusion2(){
        for(int i=0;i<3;i++){
            grayLine();
            ThickLine1();
            grayLine();
            ThickLine2();
            grayLine();
            ThickLine1();
            grayLine();
            ThickLine3();
        }
        grayLine();
        TwoWhite();
        FourBlackFourWhite5();
        FourBlack();
        TwoWhite();
        paintCorner(WHITE);
        move();
        if(frontIsBlocked()){
            paintCorner(WHITE);
        }
    }
    private void ThickLine3() {
        for(int i=0;i<3;i++){
            FourBlackFourWhite6();
            OneBlack();
            if(frontIsBlocked()){
                paintCorner(BLACK);
                moveToNextRowfromBottom();
            }
        }
    }

    private void FourBlackFourWhite6() {
        for(int i=0;i<6;i++){
            FourBlack();
            FourWhite();
        }
    }

    private void ThickLine2() {
        for(int i=0;i<3;i++){
            ThreeWhite();
            FourBlackFourWhite5();
            FourBlack();
            TwoWhite();
            if(frontIsBlocked()){
                paintCorner(WHITE);
                moveToNextRowfromBottom();
            }
        }
    }

    private void OneWhite(){
        paintCorner(WHITE);
        move();
    }

    private void OneBlack(){
        paintCorner(BLACK);
        move();
    }

    private void ThreeWhite(){
        for(int i=0;i<3;i++){
            paintCorner(WHITE);
            move();
        }
    }

    private void ThreeBlack(){
        for(int i=0;i<3;i++){
            paintCorner(BLACK);
            move();
        }
    }

    private void PaintScreenWhite(){
        turnAround();
        paintCorner(WHITE);
        while(leftIsClear()){
            PaintRowWhiteReturn();
        }
        paintCorner(WHITE);
        while(frontIsClear()){
            move();
            paintCorner(WHITE);
        }
    }

    private void PaintRowWhiteReturn() {
        paintCorner(WHITE);
        while(frontIsClear()){
            move();
            paintCorner(WHITE);
        }
        turnAround();
        moveToWall();
        moveDownRow();
    }

    private void ThickLine1(){
        for(int i=0;i<3;i++){
            TwoWhite();
            FourBlackFourWhite5();
            FourBlack();
            TwoWhite();
            paintCorner(WHITE);
            move();
            if(frontIsBlocked()){
                paintCorner(WHITE);
                moveToNextRowfromBottom();
            }
        }
    }

    private void grayLine(){
        paintCorner(LIGHT_GRAY);
        while(frontIsClear()){
            move();
            paintCorner(LIGHT_GRAY);
        }
        moveToNextRowfromBottom();
    }

    private void FourBlackFourWhite5(){
        for(int i=0;i<5;i++){
            FourBlack();
            FourWhite();
        }
    }


    private void FourBlack() {
        for(int i1=0;i1<4;i1++){
            paintCorner(BLACK);
            move();
        }
    }

    private void FourWhite(){
        for(int i1=0;i1<4;i1++){
            paintCorner(WHITE);
            move();
        }
    }

    private void OpticalIllusion1(){
        turnAround();
        for(int i=0;i<12;i++){
            FourRows();
        }
        LastTwoRows();
    }

    private void PaintScreenBlack(){
        turnAround();
        paintCorner(BLACK);
        while(leftIsClear()){
            PaintRowBlackReturn();
        }
        paintCorner(BLACK);
        while(frontIsClear()){
            move();
            paintCorner(BLACK);
        }

    }

    private void PaintRowBlackReturn(){
        paintCorner(BLACK);
        while(frontIsClear()){
            move();
            paintCorner(BLACK);
        }
        turnAround();
        moveToWall();
        moveDownRow();
    }

    private void moveDownRow(){
        turnRight();
        move();
        turnRight();
    }

    private void moveToNextRowfromBottom(){
        turnAround();
        moveToWall();
        moveToNextRow();
    }

    private void moveToNextRow(){
        turnRight();
        move();
        turnRight();
    }

    private void moveToWall(){
        while(frontIsClear()){
            move();
        }
    }

    private void TwoBlackTwoWhite12(){
        for(int i=0;i<12;i++){
            TwoBlack();
            TwoWhite();
        }
    }

    private void TwoWhiteTwoBlack12(){
        for(int i=0;i<12;i++){
            TwoWhite();
            TwoBlack();
        }
    }

    private void TwoWhite(){
        for(int i2=0;i2<2;i2++){
            paintCorner(WHITE);
            move();
        }
    }

    private void TwoBlack(){
        for(int i2=0;i2<2;i2++){
            paintCorner(BLACK);
            move();
        }
    }

    private void RowOne(){
        TwoBlackTwoWhite12();
        paintCorner(BLACK);
        move();
        if(frontIsBlocked()){
            paintCorner(BLACK);
            moveToNextRowfromBottom();
        }
    }


    private void RowTwo(){
        move();
        TwoBlackTwoWhite12();
        paintCorner(BLACK);
        moveToNextRowfromBottom();
    }

    private void RowThree(){
        TwoWhiteTwoBlack12();
        move();
        if(frontIsBlocked()){
            moveToNextRowfromBottom();
        }
    }

    private void RowFour(){
        paintCorner(BLACK);
        move();
        TwoWhiteTwoBlack12();
        moveToNextRowfromBottom();
    }

    private void FourRows(){
        RowOne();
        RowTwo();
        RowThree();
        RowFour();
    }
    private void LastTwoRows(){
        TwoBlackTwoWhite12();
        paintCorner(BLACK);
        moveToNextRowfromBottom();
        move();
        TwoBlackTwoWhite12();
        paintCorner(BLACK);
    }
}
