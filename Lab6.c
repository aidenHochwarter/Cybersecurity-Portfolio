#include <stdio.h>
#include <string.h>

void reverse(char *str){
    int i = 0;
    int length = strlen(str) - 1;
    char temp;
    while(i < length){
        temp = str[i];
        str[i] = str[length];
        str[length] = temp;
        i++;
        length--;
    }
    return;
}

int hasChar(char *str, char c){
    int i = 0;
    int length = strlen(str);
    while (i < length) {
        if (c == str[i]) {
            return 1;  
        }
        i++;
    }
    return 0;  
}

void replaceChar(char *str, char old, char new) {
    for(char *ptr = str; *ptr; ptr++)
        if(*ptr == old)
            *ptr = new;
          
}



int main(){
    char string[100];
    char swapStr[100];
    char c;
    char old;
    char new;

    printf("Enter a string: ");
    scanf("%s", string);
    reverse(string);
    printf("Reversed string: %s\n", string);

    printf("Enter a character to look for: ");
    scanf(" %c", &c);
    int found = hasChar(string, c);
    printf("Has char: %d\n", found);

    printf("Enter a string to swap letters in: ");
    scanf("%s", swapStr);
    getchar();

    printf("Enter a character to swap in the string: ");
    scanf("%c", &old);
    getchar();

    printf("Enter the character you want to swap with: ");
    scanf("%c", &new);
    getchar();

    replaceChar(swapStr, old, new);
    printf("String after swap: %s\n", swapStr);

    return 0;
}