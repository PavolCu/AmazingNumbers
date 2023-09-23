class Test {

    public static void main(String[] args) {
        new TeamLead(1);
    }

    public static class TeamLead extends Programmer {

        private final int numTeamLead;

        public TeamLead(int numTeamLead) {
            super(numTeamLead);  // This will print "1 programmer"
            this.numTeamLead = numTeamLead;
            employ();  // This will print "1 teamlead"
        }

        @Override
        protected void employ() {
            System.out.println(numTeamLead + " teamlead");
        }

    }

    public static class Programmer {

        private final int numProgrammer;

        public Programmer(int numProgrammer) {
            this.numProgrammer = numProgrammer;
            System.out.println(numProgrammer + " programmer");  // Moved from employ() method
        }

        protected void employ() {
            // Empty in the base class as the logic is now in the constructor
        }
    }
}