package uk.ac.ebi.atlas.commands;

/*
@RunWith(MockitoJUnitRunner.class)
public class RankBySpecificityAndRpkmCommandTest {

    private static final int QUEUE_SIZE = 3;

    @Mock
    private ObjectInputStream<String> largeInputStream;

    @Mock
    private ObjectInputStream<String> smallInputStream;

    private RankTopObjectsCommand<String> subject;

    @Before
    public void initializeInputStreamMock() throws Exception {
        when(largeInputStream.readNext()).thenReturn("4")
                .thenReturn("1")
                .thenReturn("7")
                .thenReturn("0")
                .thenReturn("7")
                .thenReturn("2")
                .thenReturn("5")
                .thenReturn(null);

        when(smallInputStream.readNext()).thenReturn("0")
                .thenReturn("1")
                .thenReturn(null);

    }

    @Before
    public void initializeSubject() throws Exception {
        subject = new RankTopObjectsCommand<String>().setRankingSize(QUEUE_SIZE);
    }

    @Test
    public void givenAStreamWithLessObjectsThanRankSizeTheCommandShouldReturnAllTheObjects() throws Exception {
        //when
        List<String> top3Objects = subject.apply(smallInputStream);

        //then
        assertThat(top3Objects.size(), is(2));
        //and
        assertThat(top3Objects, contains("1", "0")); //contains also checks order

    }

    @Test
    public void givenAStreamWithManyObjectsTheCommandShouldReturnThreeObjects() throws Exception {
        //when
        List<String> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects.size(), is(3));

    }

    @Test
    public void rankedObjectsShouldBeInDescendingOrder() throws Exception {
        //when
        List<String> top3Objects = subject.apply(largeInputStream);

        //then
        assertThat(top3Objects, contains("7", "7", "5")); //contains also checks order

    }

}
*/