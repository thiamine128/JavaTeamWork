package controller;

import Game.Guess;
import Game.GuessDetail;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import post.LanguageTool;
import ui.UIAnimation;
import ui.UIManager;
import ui.UINetwork;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestionController implements Initializable {

    public ImageView questionMask;
    public ImageView ans0, ans1, ans2, ans3;
    public ImageView questionCancel;
    public Text provinceName, correctNum, questionPos;
    private GuessDetail[] details;
    public Group questionGroup;
    private int thisQuestion = 1, correct = 0;
    private boolean starBool = false;
    private void resetQuestionImage(){
        UIAnimation.fadeAnimation(ans0, null, false, 300);
        UIAnimation.fadeAnimation(ans1, null, false, 300);
        UIAnimation.fadeAnimation(ans2, null, false, 300);
        UIAnimation.fadeAnimation(ans3, null, false, 300);
        UIAnimation.fadeAnimation(provinceName, event -> {
            ans0.setImage(new Image(details[thisQuestion - 1].path.get(0)));
            ans1.setImage(new Image(details[thisQuestion - 1].path.get(1)));
            ans2.setImage(new Image(details[thisQuestion - 1].path.get(2)));
            ans3.setImage(new Image(details[thisQuestion - 1].path.get(3)));
            provinceName.setText(LanguageTool.englishToChinese.get(details[thisQuestion - 1].ansProvince));
            provinceName.setFill(Color.rgb(255, 255, 255));
            UIAnimation.setBlackMask(ans0, null, 300, 0.0, 0.8);
            UIAnimation.setBlackMask(ans1, null, 300, 0.0, 0.8);
            UIAnimation.setBlackMask(ans2, null, 300, 0.0, 0.8);
            UIAnimation.setBlackMask(ans3, null, 300, 0.0, 0.8);
            UIAnimation.setBlackMask(provinceName, null, 300, 0.0, 0.8);
        }, false, 300);
    }

    public void resetQuestion() throws InterruptedException {
        UIAnimation.fadeAnimation(questionMask, null, false, 600);
        Guess.prepare();
        details = Guess.reset(3, 20);
        thisQuestion = 1;
        correct = 0;
        resetQuestionImage();
        correctNum.setText(correct+"");
        questionPos.setText(thisQuestion+"");
        starBool = true;
        updateStar();
    }

    private void chooseButton(int k){
        questionMask.setMouseTransparent(false);
        if (k == details[thisQuestion-1].ansId){
            provinceName.setText("回答正确");
            provinceName.setFill(Color.rgb(0, 155, 255));
            for (int i=1;i<=3;i++)
                UIAnimation.puzzleFireworks(50+900*Math.random(), 50+700*Math.random(),
                    questionGroup, true);
            correct++;
            correctNum.setText(correct+"");
        }else{
            provinceName.setText("回答错误");
            provinceName.setFill(Color.rgb(255, 15, 0));
            for (int i=1;i<=3;i++)
                UIAnimation.puzzleFireworks(50+900*Math.random(), 50+700*Math.random(),
                    questionGroup, false);
        }
        UIAnimation.timer(2000, event -> {
            if (thisQuestion < 20){
                thisQuestion++;
                questionPos.setText(thisQuestion+"");
                resetQuestionImage();
                questionMask.setMouseTransparent(true);
            }else{
                provinceName.setFill(Color.rgb(0, 155, 255));
                if (correct < 10) provinceName.setText("已完成所有题目");
                else {
                    provinceName.setText("恭喜你获得答题奖杯");
                    try {
                        UINetwork.setQuestionWin();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                starBool = false;
                UIAnimation.timer(3000, event1 -> {
                    questionMask.setMouseTransparent(false);
                    UIAnimation.setBlackMask(questionMask, event2 -> {
                        try {
                            UIManager.instance.toMainFrame(true);
                            questionMask.setMouseTransparent(true);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }, 600);
                });
            }
        });
    }

    private void setButtonChange(Node node){
        node.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setOpacity(0.65);
            }
        });
        node.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                node.setOpacity(0.8);
            }
        });
    }

    private void updateStar() throws InterruptedException {
        System.out.println("update star!");
        UIAnimation.starworks(questionGroup);
        UIAnimation.timer(100, event -> {
            try {
                if (starBool) updateStar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UIManager.questionController = this;
        provinceName.setText("");
        questionMask.setVisible(true);
        setButtonChange(questionCancel);
        setButtonChange(ans0);
        setButtonChange(ans1);
        setButtonChange(ans2);
        setButtonChange(ans3);
        questionCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                questionMask.setMouseTransparent(false);
                UIAnimation.setBlackMask(questionMask, event -> {
                    try {
                        starBool = false;
                        UIManager.instance.toMainFrame(true);
                        questionMask.setMouseTransparent(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 600);
            }
        });
        ans0.setOnMouseClicked(mouseEvent -> chooseButton(0));
        ans1.setOnMouseClicked(mouseEvent -> chooseButton(1));
        ans2.setOnMouseClicked(mouseEvent -> chooseButton(2));
        ans3.setOnMouseClicked(mouseEvent -> chooseButton(3));

    }

}