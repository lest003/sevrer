public class Animator {
    private String lastLine = "";

    public void print(String line) {
        if (lastLine.length() > line.length()) {
            String temp = "";
            for (int i = 0; i < lastLine.length(); i++) {
                temp += " ";
            }
            if (temp.length() > 1)
                System.out.print("\r" + temp);
        }
        System.out.print("\r" + line);
        lastLine = line;
    }

    private byte anim;

    public void animate(String line) {
        switch (anim) {
            case 1:
                print("[ \\ ] " + line);
                break;
            case 2:
                print("[ | ] " + line);
                break;
            case 3:
                print("[ / ] " + line);
                break;
            default:
                anim = 0;
                print("[ - ] " + line);
        }
        anim++;
    }

    public void animateBar() throws InterruptedException {
        String progress = " Download in progress  ";
        StringBuilder bar = new StringBuilder();
        bar.append("[                    ]");
        for (int i = 1; i < 21; i++) {
            bar.setCharAt(i, '=');
            animate(progress + bar);
            Thread.sleep(400);
        }
    }
}
