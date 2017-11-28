package com.jr.chess.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.jr.chess.Const;
import com.jr.chess.Pieces.Pawn;
import com.jr.chess.Pieces.Piece;
import com.jr.chess.Position;

import java.util.ArrayList;
import java.util.List;


public class Board extends View {
    public Board(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        startGame();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        View parent = (View) this.getParent();
        setMeasuredDimension((int) (parent.getWidth() * .95), (int) (parent.getWidth() * .95));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Position touchPosition = getSquare(new Position((int) event.getX(), (int) event.getY()));
                for(Piece i : pieces) if(i.position.x == touchPosition.x && i.position.y == touchPosition.y) i.position.y++;
                /*piece = new Piece(getSquare(new Position((int) event.getX(), (int) event.getY())).x, getSquare(new Position((int) event.getX(), (int) event.getY())).y);
                Log.v("debug_board", "piece.x=" + piece.position.x + " piece.y=" + piece.position.y);*/
                invalidate();
        }

        return super.onTouchEvent(event);
    }

    Paint fillPaint = new Paint();
    Paint strokePaint = new Paint();

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Position temp;

        fillPaint.setStyle(Paint.Style.FILL);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(14);
        for(Piece i : pieces) if(i.alive){
            if(i.color == Const.WHITE) fillPaint.setColor(Color.WHITE);
            else fillPaint.setColor(Color.BLACK);

            switch (i.type){
                case Const.PAWN:
                    temp = toPixels(i.position);
                    strokePaint.setColor(Color.BLUE);
                    canvas.drawCircle(temp.x, temp.y, 46, fillPaint);
                    canvas.drawCircle(temp.x, temp.y, 53, strokePaint);

                break;

            }

        }
    }

    private Position getSquare(Position p){
        Position squarePosition = new Position(p.x, p.y); // sidestep to avoid reference
        int oneWidth = this.getWidth() / 8;
        squarePosition.x /= oneWidth;
        squarePosition.y = 7-(squarePosition.y / oneWidth);

        return squarePosition;
    }

    private Position toPixels(Position p){
        Position pixelPosition = new Position(p.x, p.y); // sidestep to avoid reference
        int oneWidth = this.getWidth() / 8;
        pixelPosition.x = (pixelPosition.x * oneWidth) + (oneWidth / 2);
        pixelPosition.y = ((7-pixelPosition.y)*oneWidth) + (oneWidth / 2);

        return pixelPosition;
    }


    List<Piece> pieces = new ArrayList<>();
    private void startGame(){

        for (int color = Const.WHITE; color <= Const.BLACK; color++){
            /*for(int i = 0; i < 16; i++) pieces.add(new Piece(color, Const.PAWN)); TODO: reimplement pieces with new classes
            pieces.add(new Piece(color, Const.BISHOP));
            pieces.add(new Piece(color, Const.BISHOP));
            pieces.add(new Piece(color, Const.KNIGHT));
            pieces.add(new Piece(color, Const.KNIGHT));
            pieces.add(new Piece(color, Const.ROOK));
            pieces.add(new Piece(color, Const.ROOK));
            pieces.add(new Piece(color, Const.QUEEN));
            pieces.add(new Piece(color, Const.KING));
            pieces.add(new Pawn(color, Const.PAWN));*/
        }

        invalidate();
    }

}

