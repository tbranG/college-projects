#ifndef BET_CLASS
#define BET_CLASS

class Bet{
    private:
        int betCount;
        int *bets;

    public:
        Bet(){
            betCount = 0;
            bets = new int[16];
        }
        void setBetCount(int value){ betCount = value; }
        int getBetCount() { return betCount; }

        /// @return array filled with this bet cards 
        int* getBets() { return bets; }

        int getBet(int pos) { return bets[pos]; }
        int getLastBet() { return bets[betCount]; }
        
        /// @return total points in current bet  
        int getTotal() {
            int sum = 0;
            for(int i = 0; i < betCount; i++){
                sum += bets[i];
            }
            return sum;
        }

        /// @brief adds a card to player hand
        /// @param value card value
        void addToBet(int value){
            bets[betCount++] = value;
        }

        /// @param other the comparation object 
        /// @return true if the two bets have the same value
        bool operator == (Bet &other){
            return other.getTotal() == this->getTotal();
        }

        bool operator > (Bet &other){
            return this->getTotal() > other.getTotal();
        }

        bool operator < (Bet &other){
            return this->getTotal() < other.getTotal();
        }
    };

#endif