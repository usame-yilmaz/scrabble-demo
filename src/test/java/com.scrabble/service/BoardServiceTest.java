import com.scrabble.model.*;
import com.scrabble.repository.BoardJpaRespository;
import com.scrabble.service.BoardService;
import com.scrabble.service.GameService;
import com.scrabble.service.impl.BoardServiceImpl;
import com.scrabble.util.BoardUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public  class BoardServiceTest {

    @InjectMocks
    public BoardService boardService = new BoardServiceImpl();

    @Mock
    BoardJpaRespository boardJpaRespository;

    @Mock
    GameService gameService;

    @Mock
    BoardUtils boardUtils;

    @Mock
    Game game;

    @Test
    public void createBoardTest() {
        //given
        Long boardId = 1L;
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).deactivated(false).build();
        Board boardWithId = Board.builder().id(boardId).cells(new Cell[15][15]).playOrder(0).deactivated(false).build();

        //when
        when(boardJpaRespository.save(any(Board.class))).thenReturn(boardWithId);

        Board boardActual = boardService.createBoard();

        assertEquals(boardId, boardActual.getId());
    }

    @Test
    public void playTest() {
        //given
        Long boardId = 1L;
        Word word = Word.builder().characters("kitap").point(25).position(PositionEnum.HORIZONTAL).build();
        Board board = Board.builder().cells(new Cell[15][15]).playOrder(0).words(Collections.singleton(word)).cells(BoardUtils.initializeCells()).deactivated(false).build();
        Move move1 = Move.builder().letter('a').column(1).row(2).build();
        Move move2 = Move.builder().letter('r').column(1).row(3).build();
        Move move3 = Move.builder().letter('ı').column(1).row(4).build();
        Map<Character,Integer> letterPoints = new HashMap<>();
        letterPoints.put('a',1);
        letterPoints.put('r',5);
        letterPoints.put('ı',2);

        List<Move> moves = new ArrayList<>();
        moves.add(move1);
        moves.add(move2);
        moves.add(move3);

        //when
        when(boardJpaRespository.findOne(boardId)).thenReturn(board);
        when(game.getLetterPoints()).thenReturn(letterPoints);

        boardService.play(boardId, moves);

        //assertEquals(boardId, boardActual.getId());
    }
}