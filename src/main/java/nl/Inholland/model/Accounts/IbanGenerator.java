package nl.Inholland.model.Accounts;

public  class IbanGenerator {
    /*
    private void generateBban(){
        Random random = new Random();
        int length = 10;
        String digits = "0123456789";
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s ->
                result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(digits);
        appendChar.accept(digits);
        while (result.size() < length)
            appendChar.accept(digits);
        Collections.shuffle(result, rnd);
        String randomBban = String.join("", result);
        this.bban = randomBban;
    }

    public void buildIban(){
        this.countryCode = CountryCodeEnum.NL;
        if(bban == null){
            generateBban();
        }
        generateCheckDigits();
        this.ibanCode = getIbanCode();
    }



    private void generateCheckDigits(){ //from https://www.ibantest.com/en/how-is-the-iban-check-digit-calculated
        String arrangedCode = "" + codeToNumberBased(this.BANK) + "" + this.bban + "" + codeToNumberBased(this.countryCode.toString()) + "00";

        BigDecimal arrangedCodeNum = new BigDecimal(arrangedCode);
        BigDecimal checkDigits = new BigDecimal(98).subtract(arrangedCodeNum.remainder(new BigDecimal(97)));
        DecimalFormat df = new DecimalFormat("00");
        this.checkDigits = df.format(checkDigits);

    }
     */
}
