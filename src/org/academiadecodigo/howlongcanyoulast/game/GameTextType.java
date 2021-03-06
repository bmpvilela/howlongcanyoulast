package org.academiadecodigo.howlongcanyoulast.game;

/**
 * Created by andre on 21/06/2016.
 *
 * Enum of the messages on the game
 * Method which returns the correspondent string to enum
 */
public enum GameTextType {
    TIMEOUT,
    READY,
    GO,
    WAITING;

    /**
     * Getter
     *
     * @param textType Type to search
     * @return Text to be shown
     */
    public static String[] getText(GameTextType textType) {
        switch (textType) {
            case TIMEOUT:
                return getTimeOut();

            case READY:
                return getReady();

            case GO:
                return getGo();

            case WAITING:
                return getWaiting();

            default:
                System.out.println("Enum TxtType return null");
                return null;
        }
    }

    /**
     * Getter
     *
     * @return
     */
    private static String[] getTimeOut() {
        return new String[]{
                "######## ####### ###   ### #######",
                "   ##      ###   ## # # ## ##",
                "   ##      ###   ##  #  ## #####",
                "   ##      ###   ##     ## ##",
                "   ##    ####### ##     ## #######",
                "",
                "     ######## ##    ## ########",
                "     ##    ## ##    ##    ##   ",
                "     ##    ## ##    ##    ##   ",
                "     ##    ## ##    ##    ##   ",
                "     ######## ########    ##   "};
    }

    /**
     * Getter
     *
     * @return
     */
    private static String[] getReady() {
        return new String[]{"####### #######     ###     #####   ##   ##",
                            "##   ## ##         ## ##    ##  ##   ## ##",
                            "######  #####     ##   ##   ##   ##   ###",
                            "##  ##  ##       ## ##  ##  ##  ##    ###",
                            "##   ## ####### ##       ## #####     ###"};
    }

    /**
     * Getter
     *
     * @return
     */
    private static String[] getGo() {
        return new String[]{"######## ########",
                            "##       ##    ##",
                            "##  #### ##    ##",
                            "##    ## ##    ##",
                            "######## ########"};
    }

    /**
     * Getter
     *
     * @return
     */
    private static String[] getWaiting() {
        return new String[]{
                "##           ##  ###     ####### ######## ####### ###    ## ########",
                " ##    #    ##  ## ##      ###      ##      ###   ## #   ## ##      ",
                "  ##  ###  ##  ##   ##     ###      ##      ###   ##  #  ## ##  ####",
                "   #### ####  ## ##  ##    ###      ##      ###   ##   # ## ##    ##",
                "    ##   ##  ##       ## #######    ##    ####### ##    ### ########"};
    }
}
